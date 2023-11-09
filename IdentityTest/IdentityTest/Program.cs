<<<<<<<< HEAD:Musica/Program.cs
using Microsoft.EntityFrameworkCore;
using Musica.Data;
using System.Configuration;
========
using IdentityTest.Data;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
>>>>>>>> f06e521 (Commit):IdentityTest/IdentityTest/Program.cs

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseSqlServer(connectionString));
builder.Services.AddDatabaseDeveloperPageExceptionFilter();

builder.Services.AddDefaultIdentity<IdentityUser>(options => options.SignIn.RequireConfirmedAccount = false)
    .AddEntityFrameworkStores<ApplicationDbContext>();
builder.Services.AddControllersWithViews();
<<<<<<<< HEAD:Musica/Program.cs

var connectionString = builder.Configuration.GetConnectionString("DbChinook");

builder.Services.AddDbContext<ChinookContext>(options =>
    options.UseSqlServer(connectionString));
========
>>>>>>>> f06e521 (Commit):IdentityTest/IdentityTest/Program.cs

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseMigrationsEndPoint();
}
else
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthentication();
app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");
app.MapRazorPages();

app.Run();