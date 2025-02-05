﻿using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace api.Models;


[Table("users")]
public class User
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("first_name")]
    [MinLength(3)]
    [Required]
    public required string FirstName { get; set; }

    [Column("last_name")]
    [MinLength(3)]
    [Required]
    public required string LastName { get; set; }

    [Column("email")]
    [EmailAddress]
    [Required]
    public required string Email { get; set; }

    [Column("dob")]
    public DateOnly? BirthDate { get; set; }

    [Column("phone_number")]
    [Phone]
    public string? PhoneNumber { get; set; }

    [Column("password")]
    [MinLength(6)]
    [Required]
    public required string Password { get; set; }
}
