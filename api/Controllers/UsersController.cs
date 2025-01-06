using api.Models;
using api.Models.Context;
using api.Models.Token;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers;


[ApiController]
[Route("[controller]")]
public class UsersController(MainContext ctx, TokenGenerator generator, IUserRepository userRepository, ITokenRepository tokenRepository) : Controller
{
    private readonly TokenGenerator generator = generator;
    private readonly IUserRepository _userRepository = userRepository;
    private readonly ITokenRepository _tokenRepository = tokenRepository;

    [HttpGet("me")]
    [Authorize]
    public ActionResult<User> GetUserInfo()
    {
        int? userId = _tokenRepository.GetIdFromToken(User);
        if (userId is null) return BadRequest("User ID missing from token");

        User? user = _userRepository.GetUserById((int)userId);

        if (user is null) return NotFound("User not found");

        return Ok(user);
    }

    [HttpPost("login")]
    public ActionResult Login([FromBody] LoginModel model)
    {
        var user = _userRepository.GetByEmail(model.Email);

        if (user is null) return NotFound("Invalid email.");

        if (!BCrypt.Net.BCrypt.Verify(model.Password, user.Password))
            return NotFound("Invalid password.");

        var token = generator.GenerateJWTToken(user);

        return Ok(new { token });
    }

    [HttpPost("signup")]
    [ProducesResponseType(500)]
    public async Task<IActionResult> SignUp([FromBody] SignUpModel model)
    {

        if (!_userRepository.IsValidEmail(model.Email))
            return BadRequest("User with the same email already exists");

        var hashedPassword = BCrypt.Net.BCrypt.HashPassword(model.Password);

        var user = new User
        {
            FirstName = model.FirstName,
            LastName = model.LastName,
            Email = model.Email,
            Password = hashedPassword,
        };

        await _userRepository.AddUser(user);

        var token = generator.GenerateJWTToken(user);

        return Ok(new { token });
    }

    [HttpPatch]
    [Authorize]
    public async Task<ActionResult<User>> Update([FromBody] JsonPatchDocument<User> patchDoc)
    {
        int? userId = _tokenRepository.GetIdFromToken(User);
        if (userId is null) return BadRequest("User ID missing from token");

        User? user = _userRepository.GetUserById((int)userId);

        if (user is null) return NotFound("Invalid user id.");

        patchDoc.ApplyTo(user);

        // Validate the user, because when passing JsonPatchDocument,
        // the underlying user object was not properly validated
        TryValidateModel(user);

        if (!ModelState.IsValid)
            return BadRequest("Invalid parameters");

        await _userRepository.UpdateUser(user, patchDoc);

        return Ok(user);
    }

    [HttpGet("addresses")]
    [Authorize]
    public IActionResult GetAddresses()
    {
        int? id = _tokenRepository.GetIdFromToken(User);
        if (id is null) return BadRequest("User ID missing from token");

        int userId = (int)id;

        var addresses = ctx.Addresses.Where(a => a.UserId == userId);

        return Ok(addresses);
    }

    [HttpPost("addresses")]
    [Authorize]
    public async Task<IActionResult> AddAddress([FromBody] Address address)
    {
        int? id = _tokenRepository.GetIdFromToken(User);
        if (id is null) return BadRequest("User ID missing from token");

        address.Id = 0;
        address.UserId = (int)id;

        await ctx.Addresses.AddAsync(address);

        await ctx.SaveChangesAsync();

        return Ok();
    }
}
