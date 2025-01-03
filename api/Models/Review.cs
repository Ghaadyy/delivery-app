using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace api.Models;

public class Review 
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;

    [ForeignKey("RestaurantId")]
    public required int RestaurantId { get; set; }
    public required string ReviewerName { get; set; }
    public required double Rating { get; set; }
    public required string Comments { get; set; }
}