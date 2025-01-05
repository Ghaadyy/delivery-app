using Microsoft.EntityFrameworkCore;

namespace api.Models.Context;

#pragma warning disable CS8618 // Non-nullable field must contain a non-null value when exiting constructor. Consider adding the 'required' modifier or declaring as nullable.
public class MainContext(DbContextOptions options) : DbContext(options)
{
    /* Restaurant related */
    public DbSet<Restaurant> Restaurants { get; set; }
    public DbSet<Menu> Menus { get; set; }
    public DbSet<MenuSection> MenuSections { get; set; }
    public DbSet<Meal> Meals { get; set; }
    public DbSet<MealOption> MealOptions { get; set; }
    public DbSet<MealUpgrade> MealUpgrades { get; set; }
    public DbSet<Review> Reviews { get; set; }
    public DbSet<User> Users { get; set; }

    /* Orders related */
    public DbSet<Order> Orders { get; set; }
    public DbSet<Address> Addresses { get; set; }
    public DbSet<OrderDetail> OrderDetails { get; set; }
}