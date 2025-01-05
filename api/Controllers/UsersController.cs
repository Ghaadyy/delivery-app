using api.Models;
using api.Models.Token;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers;


[ApiController]
[Route("[controller]")]
public class UsersController(TokenGenerator generator, IUserRepository userRepository, ITokenRepository tokenRepository) : Controller
{
    private readonly TokenGenerator generator = generator;
    private readonly IUserRepository _userRepository = userRepository;
    private readonly ITokenRepository _tokenRepository = tokenRepository;

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
}
