using System;
using api.Models.Context;
using Microsoft.AspNetCore.JsonPatch;

namespace api.Models;

public class UserRepository(MainContext context) : IUserRepository
{
    private readonly MainContext context = context;

    public User? GetUserById(int userId)
    {
        var user = (from u in context.Users
                    where u.Id == userId
                    select u).FirstOrDefault();

        return user;
    }

    public User? GetByEmail(string email)
    {
        var user = (from u in context.Users
                    where u.Email == email
                    select u).FirstOrDefault();

        return user;
    }

    public async Task AddUser(User user)
    {
        context.Users.Add(user);
        await context.SaveChangesAsync();
    }

    public User? AuthenticateUser(string email, string password)
    {
        var user = (from u in context.Users
                    where u.Email == email && u.Password == password
                    select u).FirstOrDefault();

        return user;
    }

    public bool IsValidEmail(string email)
    {
        return (from u in context.Users
                where u.Email == email
                select u).FirstOrDefault() is null;
    }

    public async Task UpdateUser(User user, JsonPatchDocument<User> patchDoc)
    {
        patchDoc.ApplyTo(user);
        await context.SaveChangesAsync();
    }
}