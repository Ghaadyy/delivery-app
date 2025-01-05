using Microsoft.AspNetCore.JsonPatch;

namespace api.Models;

public interface IUserRepository
{
    User? GetUserById(int userId);
    User? GetByEmail(string email);
    User? AuthenticateUser(string email, string password);
    Task AddUser(User user);
    bool IsValidEmail(string email);}