using Microsoft.EntityFrameworkCore;
using UT03_Ej02_AndresIzquierdo.Data;

namespace UT03_Ej02_AndresIzquierdo.Models
{
    public static class SeedData
    {
        public static void Initialize(IServiceProvider serviceProvider)
        {
            using (var context = new UT03_Ej02_AndresIzquierdoContext(serviceProvider.GetRequiredService < DbContextOptions<UT03_Ej02_AndresIzquierdoContext>> ())) 
            {
                if (context.Game.Any()) 
                {
                    return;
                }

                context.Genre.AddRange(
                );

                context.Game.AddRange(
                );

                

                context.SaveChanges();
            }
        }
    }
}
