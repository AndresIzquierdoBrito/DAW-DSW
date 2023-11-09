using Microsoft.EntityFrameworkCore;
<<<<<<<< HEAD:MVCMigrationMuiscRename/Program.cs
using MVCMigrationMuiscRename.Data;
========
using Microsoft.AspNetCore.Identity;
using IzquierdoAndres_Musica.Areas.Identity.Data;
>>>>>>>> f06e521 (Commit):IzquierdoAndres_Musica_Identity/Program.cs

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();

<<<<<<<< HEAD:MVCMigrationMuiscRename/Program.cs
builder.Services.AddDbContext<ChinookContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DbChinook")));
========
builder.Services.AddDbContext<LocalDBChinookContext>(options => 
    options.UseSqlServer(builder.Configuration.GetConnectionString("DbChinook")));

builder.Services.AddDbContext<AppAutContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("AppAutContextConnection")));

builder.Services.AddDefaultIdentity<IdentityUser>(options => options.SignIn.RequireConfirmedAccount = false)
    .AddEntityFrameworkStores<AppAutContext>();
>>>>>>>> f06e521 (Commit):IzquierdoAndres_Musica_Identity/Program.cs

var app = builder.Build();


// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
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
