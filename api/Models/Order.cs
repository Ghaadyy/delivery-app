using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Net.NetworkInformation;

namespace api.Models;

public class OrderRequest
{
    public required int RestaurantId;
    public required List<MealRequest> mealRequests;
    public required int AddressId;
    public required string PaymentMethod;
}

public class MealRequest
{
    public required Meal Meal;
    public required int Quantity;
    public required List<MealOption> Options;
}

public class Order
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; } = 0;
    public required int Customer { get; set; }

    [ForeignKey("RestaurantId")]
    public required int RestaurantId { get; set; }
    public required OrderStatus OrderStatus { get; set; }
    public required Driver? Driver { get; set; }
    public required string OrderDate { get; set; }
    public required string DeliveredDate { get; set; }

    [ForeignKey("AddressId")]
    public required Address OrderAddress { get; set; }
    public required double Subtotal { get; set; }
    public required double DiscountedPrice { get; set; }
    public required double DeliveryCharge { get; set; }
    public required string PaymentMethod { get; set; }
    public required OrderRating OrderRating { get; set; }
    public required DriverRating DriverRating { get; set; }
}

public enum OrderStatus
{
    CONFIRMING = 1,
    PREPARING_FETCHING_DRIVER = 2,
    PREPARING_DRIVER_GOING_TO_STORE = 3,
    PREPARING_DRIVER_IN_STORE = 4,
    ON_THE_WAY = 5,
    DELIVERED = 6
}

public enum DriverRating
{
    LIKE = 1,
    DISLIKE = -1,
    PENDING = 0,
    NOT_APPLICABLE = -2
};

public enum OrderRating
{
    EXCELLENT = 5,
    GOOD = 4,
    AVERAGE = 3,
    POOR = 2,
    TERRIBLE = 1,
    PENDING = 0,
    NOT_APPLICABLE = -1
};

public class OrderDetail
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }

    public required Order Order { get; set; }

    public required Meal Meal { get; set; }

    public required double TotalPrice { get; set; }

    public required int Quantity { get; set; }
}