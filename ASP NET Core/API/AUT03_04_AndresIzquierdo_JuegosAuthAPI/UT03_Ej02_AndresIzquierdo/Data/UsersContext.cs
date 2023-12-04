
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace AUT03_04_AndresIzquierdo_JuegosAuthAPI.Data
{

    public class UsersContext : IdentityDbContext<IdentityUser>
    {
        public UsersContext(DbContextOptions<UsersContext> options)
            : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            List<IdentityRole> roles = new List<IdentityRole>
            {
                new IdentityRole
                {
                    Name = "User",
                    NormalizedName = "USER"
                },
                new IdentityRole
                {
                    Name = "Admin",
                    NormalizedName = "ADMIN"
                },
                new IdentityRole
                {
                    Name = "Guest",
                    NormalizedName = "GUEST"
                }
            };

            modelBuilder.Entity<IdentityRole>().HasData(roles);

            List<IdentityUser> users = new List<IdentityUser>
            {
                new IdentityUser
                {
                    UserName = "user@ejercicio3.com",
                    NormalizedUserName = "USER@EJERCICIO3.COM"
                },
                new IdentityUser
                {
                    UserName = "admin@ejercicio3.com",
                    NormalizedUserName = "ADMIN@EJERCICIO3.COM"
                },
                new IdentityUser
                {
                    UserName = "guest@ejercicio3.com",
                    NormalizedUserName = "guest@EJERCICIO3.COM"
                }
            };

            modelBuilder.Entity<IdentityUser>().HasData(users);

            var passwordHasher = new PasswordHasher<IdentityUser>();
            users[0].PasswordHash = passwordHasher.HashPassword(users[0],
            "User1234!");
            users[1].PasswordHash = passwordHasher.HashPassword(users[0],
            "Admin1234!");
            users[2].PasswordHash = passwordHasher.HashPassword(users[0],
            "Guest1234!");

            List<IdentityUserRole<string>> userRoles = new List<IdentityUserRole<string>>
            {
                new IdentityUserRole<string>
                {
                    UserId= users[0].Id,
                    RoleId= roles[0].Id
                },
                new IdentityUserRole<string>
                {
                    UserId= users[1].Id,
                    RoleId= roles[1].Id
                },
                new IdentityUserRole<string>
                {
                    UserId= users[2].Id,
                    RoleId= roles[2].Id
                }
            };

            modelBuilder.Entity<IdentityUserRole<string>>().HasData(userRoles);

            base.OnModelCreating(modelBuilder);
        }

    }

}
