using api.Models.Context;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers().AddNewtonsoftJson();
builder.Services.AddDbContextPool<MainContext>(opt => 
    opt.UseNpgsql(builder.Configuration.GetConnectionString("DeliveryAppDB")));

var app = builder.Build();

app.MapControllers();

app.Run();
