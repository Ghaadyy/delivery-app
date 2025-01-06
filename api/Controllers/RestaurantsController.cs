using Microsoft.AspNetCore.Mvc;
using api.Models;
using api.Models.Context;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http.Features;
using Microsoft.EntityFrameworkCore.Internal;

namespace api.Controllers;

[ApiController]
[Route("[controller]")]
[Authorize]
public class RestaurantsController(MainContext ctx) : Controller
{
    [HttpGet]
    public IEnumerable<dynamic> Get() => ctx.Restaurants.Select(r => new
    {
        r.Id,
        r.Location,
        r.Name,
        Rating = Math.Round(ctx.Reviews
                    .AsSplitQuery()
                    .Where(rev => rev.RestaurantId == r.Id)
                    .DefaultIfEmpty()
                    .Average(rev => rev == null ? 0 : rev.Rating), 1)
    });

    [HttpGet("{id}")]
    public IActionResult Get(int id)
    {
        var rest = ctx.Restaurants.First(rest => rest.Id == id);

        var reviews = ctx.Reviews.Where(r => r.RestaurantId == id);

        return Ok(new
        {
            rest.Id,
            rest.Location,
            rest.Name,
            Rating = reviews.Any() ? Math.Round(reviews.Select(r => r.Rating).Average(), 1) : 0
        });
    }

    [HttpGet("{id}/menu")]
    public Menu GetMenu(int id) => ctx.Menus
        .AsSplitQuery()
        .Include(m => m.Sections)
        .ThenInclude(ms => ms.Meals)
        .ThenInclude(m => m.Upgrades)
        .ThenInclude(up => up.Options)
        .FirstOrDefault(m => m.RestaurantId == id) ?? new Menu
        {
            Sections = [],
            RestaurantId = id,
        };

    [HttpGet("{id}/reviews")]
    public IEnumerable<Review> GetReviews(int id) => ctx.Reviews
        .Where(r => r.RestaurantId == id);


    [HttpPost("{id}/reviews")]
    public async Task<IActionResult> AddReview(int id, [FromBody] Review review)
    {
        var newReview = new Review
        {
            ReviewerName = review.ReviewerName,
            RestaurantId = id,
            Comments = review.Comments,
            Rating = review.Rating,
        };

        await ctx.Reviews.AddAsync(newReview);

        await ctx.SaveChangesAsync();

        return Ok();
    }
}