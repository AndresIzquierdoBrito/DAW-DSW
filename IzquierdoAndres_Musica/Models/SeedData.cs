using IzquierdoAndres_Musica.Data;
using Microsoft.EntityFrameworkCore;

namespace IzquierdoAndres_Musica.Models
{
    public static class SeedData
    {
        public static void Initialize(IServiceProvider serviceProvider)
        {
            using (var context = new LocalDBChinookContext(
                    serviceProvider.GetRequiredService<DbContextOptions<LocalDBChinookContext>>()))
            {
                if (context.Review.Any())
                {
                    return;
                }

                context.Review.AddRange(
                    new Review
                    {
                        Title = "Sample Title", 
                        Description = "Sample Description",
                        Rating = 4
                    }
                );
                context.SaveChanges();
            }
        }
    }
}
