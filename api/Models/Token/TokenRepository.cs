using System.Security.Claims;

namespace api.Models.Token;

public class TokenRepository : ITokenRepository
{
    public int? GetIdFromToken(ClaimsPrincipal claim)
    {
        var nameIdentifier = claim.FindFirst(ClaimTypes.NameIdentifier)?.Value;

        if (nameIdentifier is null || !int.TryParse(nameIdentifier, out int userId))
        {
            return null;
        }

        return userId;
    }
}
