using System.Security.Claims;

namespace api.Models.Token;

public interface ITokenRepository
{
    int? GetIdFromToken(ClaimsPrincipal claim);
}
