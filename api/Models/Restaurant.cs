using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace api.Models;

public class Restaurant
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;
    public required string Name { get; set; }
    public required string Location { get; set; }
}

public class Menu
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;

    [ForeignKey("RestaurantId")]
    public required int RestaurantId { get; set; }
    public required ICollection<MenuSection> Sections { get; set; } = new List<MenuSection>();
}

public class MenuSection
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;
    public required string Name { get; set; }
    public required ICollection<Meal> Meals { get; set; } = new List<Meal>();
}

public class Meal
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0; 
    public required string Name { get; set; }
    public required string Ingredients { get; set; }
    public required double Price { get; set; }
    public required ICollection<MealUpgrade> Upgrades { get; set; } = new List<MealUpgrade>();
}

public class MealUpgrade
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;
    public required string Name { get; set; }
    public required ICollection<MealOption> Options { get; set; } = new List<MealOption>();
    public required bool Multiple { get; set; }
}

public class MealOption
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;
    public required string Name { get; set; }
    public required double Price { get; set; }
}

