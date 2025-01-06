using Microsoft.AspNetCore.Mvc;
using api.Models;
using api.Models.Context;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authorization;
using api.Models.Token;

namespace api.Controllers;

[ApiController]
[Route("[controller]")]
[Authorize]
public class OrdersController(MainContext ctx, ITokenRepository tokenRepository) : Controller
{
    [HttpGet]
    public IActionResult Get()
    {
        int? userId = tokenRepository.GetIdFromToken(User);
        if (userId is null) return BadRequest("User ID is missing from token");

        var orders = from order in ctx.Orders.Include(o => o.OrderAddress)
                     join rest in ctx.Restaurants on order.RestaurantId equals rest.Id
                     where order.Customer == userId
                     select new
                     {
                         order.Customer,
                         order.DeliveredDate,
                         order.DeliveryCharge,
                         order.DiscountedPrice,
                         order.Driver,
                         order.DriverRating,
                         order.Id,
                         order.OrderAddress,
                         order.OrderDate,
                         order.OrderRating,
                         order.OrderStatus,
                         order.PaymentMethod,
                         order.Subtotal,
                         RestaurantId = rest.Name // this is temporary
                     };

        return Ok(orders);
    }

    [HttpGet("{id}")]
    public dynamic Get(int id)
    {
        int? userId = tokenRepository.GetIdFromToken(User);
        if (userId is null) return BadRequest("User ID is missing from token");

        var orders = from order in ctx.Orders.Include(o => o.OrderAddress)
                     where order.Id == id && order.Customer == userId
                     join rest in ctx.Restaurants on order.RestaurantId equals rest.Id
                     select order;

        if (orders.First().Id != userId)
        {
            return BadRequest("Could not find order");
        }

        return Ok(orders);
    }

    [HttpPost]
    public async Task<IActionResult> AddOrder([FromBody] OrderRequest req)
    {
        int? id = tokenRepository.GetIdFromToken(User);
        if (id is null) return BadRequest("User ID is missing from token");

        var orderAddress = ctx.Addresses.First(a => a.Id == req.AddressId);

        var mealRequests = req.mealRequests;

        double subtotal = 0;

        foreach (var mealRequest in mealRequests)
        {
            var meal = mealRequest.Meal;

            if (ctx.Meals.Any(m => m.Id == meal.Id))
            {
                ctx.Attach(meal);
            }

            foreach (var opt in mealRequest.Options)
            {
                subtotal += opt.Price * mealRequest.Quantity;
            }

            subtotal += meal.Price * mealRequest.Quantity;
        }

        var order = new Order
        {
            Id = 0,
            Customer = (int)id,
            DeliveredDate = "",
            DeliveryCharge = 3,
            DiscountedPrice = 0,
            Driver = null,
            DriverRating = DriverRating.NOT_APPLICABLE,
            OrderAddress = orderAddress,
            OrderDate = DateOnly.FromDateTime(DateTime.Now).ToLongDateString(),
            OrderRating = OrderRating.NOT_APPLICABLE,
            OrderStatus = OrderStatus.CONFIRMING,
            PaymentMethod = req.PaymentMethod,
            RestaurantId = req.RestaurantId,
            Subtotal = subtotal
        };

        List<OrderDetail> details = [];

        foreach (var mr in mealRequests)
        {
            var detail = new OrderDetail
            {
                Meal = mr.Meal,
                Order = order,
                Quantity = mr.Quantity,
                TotalPrice = (mr.Meal.Price + mr.Options.Sum(o => o.Price)) * mr.Quantity
            };

            details.Add(detail);
        }

        await ctx.OrderDetails.AddRangeAsync(details);

        await ctx.Orders.AddAsync(order);

        await ctx.SaveChangesAsync();

        return Ok();
    }

    [HttpGet("{id}/details")]
    public IEnumerable<OrderDetail> GetDetails(int id) =>
        from details in ctx.OrderDetails.Include(d => d.Order).Include(d => d.Meal)
        where details.Order.Id == id
        select details;
}