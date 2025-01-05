using System;
using System.ComponentModel.DataAnnotations;

namespace api.Models;

public class SignUpModel
{
    [Required]
    [MinLength(3)]
    public required string FirstName { get; set; }

    [Required]
    [MinLength(3)]
    public required string LastName { get; set; }

    [Required]
    [EmailAddress]
    public required string Email { get; set; }

    [Required]
    [MinLength(6)]
    public required string Password { get; set; }
}