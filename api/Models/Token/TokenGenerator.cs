using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace api.Models.Token;

public class TokenGenerator(IConfiguration configuration)
{
    private readonly IConfiguration configuration = configuration;

    public string GenerateJWTToken(User user)
    {
        var claims = new List<Claim>
        {
            new(ClaimTypes.NameIdentifier, user.Id.ToString())
        };

        var jwtToken = new JwtSecurityToken(
            claims: claims,
            notBefore: DateTime.UtcNow,
            expires: DateTime.UtcNow.AddHours(1),
            signingCredentials: new SigningCredentials(
                new SymmetricSecurityKey(
                   Encoding.UTF8.GetBytes(configuration["JWT:Secret"]!)
                ),
                SecurityAlgorithms.HmacSha256Signature)
            );

        return new JwtSecurityTokenHandler().WriteToken(jwtToken);
    }
}
