namespace api.Models;

public record Location(double Latitude, double Longitude);

public record Order(
    int Id,
    int CustomerId,
    string RestaurantId,
    OrderStatus OrderStatusId,
    Driver? Driver,
    string OrderDate,
    string DeliveredDate,
    Location OrderLocation,
    double Subtotal,
    double DiscountedPrice,
    double DeliveryCharge,
    string PaymentMethod,
    OrderRating OrderRatingId,
    DriverRating DriverRatingId
);

public record Driver(
    string Id,
    string Name,
    string StartDate,
    int Orders
);

public enum OrderStatus
{
    CONFIRMING = 1 ,
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

public record OrderDetail(
    int Id,
    int OrderId,
    string ItemId,
    double TotalPrice,
    int Quantity
);