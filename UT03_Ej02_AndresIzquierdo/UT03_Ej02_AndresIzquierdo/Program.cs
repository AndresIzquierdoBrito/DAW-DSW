using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using System.Text.Json.Serialization;
using UT03_Ej02_AndresIzquierdo.Data;
//using UT03_Ej02_AndresIzquierdo.Data;
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddDbContext<UT03_Ej02_AndresIzquierdoContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("UT03_Ej02_AndresIzquierdoContext") ?? throw new InvalidOperationException("Connection string 'UT03_Ej02_AndresIzquierdoContext' not found.")));
// Add services to the container.

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddControllers().AddJsonOptions(x => 
    x.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles);

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
