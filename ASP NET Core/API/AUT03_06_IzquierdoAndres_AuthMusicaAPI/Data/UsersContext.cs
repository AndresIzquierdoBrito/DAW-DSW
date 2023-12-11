using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;

namespace AUT03_06_IzquierdoAndres_AuthMusicaAPI.Data
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
                    Name = "Manager",
                    NormalizedName = "MANAGER"
                }
            };

            modelBuilder.Entity<IdentityRole>().HasData(roles);

            List<IdentityUser> users = new List<IdentityUser>
            {
                new IdentityUser
                {
                    UserName = "standardUser",
                    NormalizedUserName = "STANDARDUSER"
                },
                new IdentityUser
                {
                    UserName = "adminUser",
                    NormalizedUserName = "ADMINUSER"
                },
                new IdentityUser
                {
                    UserName = "managerUser",
                    NormalizedUserName = "MANAGERUSER"
                }
            };

            modelBuilder.Entity<IdentityUser>().HasData(users);

            var passwordHasher = new PasswordHasher<IdentityUser>();
            users[0].PasswordHash = passwordHasher.HashPassword(users[0],
            "Asdf1234!");
            users[1].PasswordHash = passwordHasher.HashPassword(users[0],
            "Asdf1234!");
            users[2].PasswordHash = passwordHasher.HashPassword(users[0],
            "Asdf1234!");

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
