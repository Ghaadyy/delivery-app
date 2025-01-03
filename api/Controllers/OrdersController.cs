using Microsoft.AspNetCore.Mvc;
using api.Models;

namespace api.Controllers;

[Route("[controller]")]
public class OrdersController : Controller
{
    static readonly List<Order> orders = new()
    {
        new Order(
            1,
            1,
            "Cheese on Top",
            OrderStatus.DELIVERED,
            new Driver("20", "Antoine Karam", "2019-11-20", 9920),
            "2024-11-14",
            "2024-11-14",
            new(0.0, 0.0),
            50.0,
            5.0,
            2.0,
            "Credit Card",
            OrderRating.EXCELLENT,
            DriverRating.LIKE
        ),
        new Order(
            0,
            1,
            "Pizza Hut",
            OrderStatus.DELIVERED,
            new Driver("10", "Thor", "2023-11-20", 129),
            "2024-11-14",
            "2024-11-14",
            new(0.0, 0.0),
            100.0,
            10.0,
            1.0,
            "Credit Card",
            OrderRating.PENDING,
            DriverRating.PENDING
        ),
        new Order(
            2,
            1,
            "Boneless",
            OrderStatus.CONFIRMING,
            null,
            "2024-11-13",
            "2024-11-13",
            new(33.8928, 35.5118),
            350.0,
            3.0,
            1.0,
            "Cash",
            OrderRating.NOT_APPLICABLE,
            DriverRating.NOT_APPLICABLE
        ),
        new Order(
            3,
            1,
            "Eddy's Boneless",
            OrderStatus.PREPARING_FETCHING_DRIVER,
            null,
            "2024-11-13",
            "2024-11-13",
            new(33.8928, 35.5118),
            10.0,
            0.0,
            1.0,
            "Cash",
            OrderRating.NOT_APPLICABLE,
            DriverRating.NOT_APPLICABLE
        ),
        new Order(
            4,
            1,
            "Sandwich W Noss",
            OrderStatus.PREPARING_DRIVER_IN_STORE,
            new Driver("4", "Super Saiyan 4 Gogeta", "2022-11-20", 1230),
            "2024-11-20",
            "2024-11-21",
            new(33.8928, 35.5118),
            7.0,
            0.0,
            1.0,
            "Cash",
            OrderRating.NOT_APPLICABLE,
            DriverRating.NOT_APPLICABLE
        )
    };

    [HttpGet]
    public IEnumerable<Order> Get() => orders;

    [HttpGet("{id}/details")]
    public IEnumerable<OrderDetail> Get(int id) => new List<OrderDetail>()
    {
        new(1, id, "Melt Your Brie Burger", 9.5, 1),
        new(2, id, "Oh My Cheddar Burger", 20.0, 2)
    };
}