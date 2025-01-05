using Microsoft.AspNetCore.Mvc;
using api.Models;
using api.Models.Context;
using Microsoft.EntityFrameworkCore;

namespace api.Controllers;

[Route("[controller]")]
public class OrdersController(MainContext ctx) : Controller
{
    [HttpGet]
    public IEnumerable<dynamic> Get() => from order in ctx.Orders.Include(o => o.OrderAddress)
                                         join rest in ctx.Restaurants on order.RestaurantId equals rest.Id
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

    [HttpGet("{id}")]
    public dynamic Get(int id) => from order in ctx.Orders.Include(o => o.OrderAddress)
                                  where order.Id == id
                                  join rest in ctx.Restaurants on order.RestaurantId equals rest.Id
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

    [HttpPost]
    public async Task<IActionResult> AddOrder([FromBody] OrderRequest req)
    {
        var meal = ctx.Meals.First(m => m.Id == req.MealId);
        var orderAddress = ctx.Addresses.First(a => a.Id == req.AddressId);
        var options = ctx.MealOptions.Where(opt => req.OptionIds.Contains(opt.Id));
        var optionsPrice = options.Select(o => o.Price).Sum();
        var subtotal = meal.Price * req.Quantity + optionsPrice;

        var order = new Order
        {
            Id = 0,
            Customer = 1,
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

        var details = new OrderDetail
        {
            Meal = meal,
            Order = order,
            Quantity = req.Quantity,
            TotalPrice = subtotal
        };

        await ctx.OrderDetails.AddAsync(details);

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