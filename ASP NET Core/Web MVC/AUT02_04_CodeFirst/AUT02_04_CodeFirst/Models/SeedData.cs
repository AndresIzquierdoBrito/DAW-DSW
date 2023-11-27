using AUT02_04_CodeFirst.Data;
using Microsoft.EntityFrameworkCore;

namespace AUT02_04_CodeFirst.Models
{
    public static class SeedData
    {
        public static void Initialize(IServiceProvider serviceProvider)
        {
            using (var context = new AUT02_04_CodeFirstContext(
                serviceProvider.GetRequiredService<DbContextOptions<AUT02_04_CodeFirstContext>>()))
            {
                if (context.Recipe.Any())
                {
                    return;
                }

                context.Recipe.AddRange(
                    new Recipe
                    {
                        Name = "Test",
                        Description = "Test",
                        KCalAmount = 1,
                        Ingredients = "Test"
                    }
                );

                context.SaveChanges();
            }
        }
    }
}
