using IzquierdoAndres_Musica.Data;
<<<<<<< HEAD
using Microsoft.EntityFrameworkCore;
=======
>>>>>>> e793442 (Commit)

namespace IzquierdoAndres_Musica.Models
{
    public static class SeedData
    {
<<<<<<< HEAD
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
=======
        public static void Initializa(IServiceProvider serviceProvider)
        {
            //using (var context = new LocalDBChinookContext) {
            //}
>>>>>>> e793442 (Commit)
        }
    }
}
