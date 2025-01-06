using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace api.Models;

public class Address
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;

    [ForeignKey("UserId")]
    public required int UserId { get; set; } = 0;

    public required double Latitude { get; set; }

    public required double Longitude { get; set; }

    public string? Street { get; set; }
    public string? Building { get; set; }
    public string? Apartment { get; set; }
}