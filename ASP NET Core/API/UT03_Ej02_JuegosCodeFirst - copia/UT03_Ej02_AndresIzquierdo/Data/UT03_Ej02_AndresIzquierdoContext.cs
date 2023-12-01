using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using UT03_Ej02_AndresIzquierdo.Models;

namespace UT03_Ej02_AndresIzquierdo.Data
{
    public class UT03_Ej02_AndresIzquierdoContext : DbContext
    {
        public UT03_Ej02_AndresIzquierdoContext (DbContextOptions<UT03_Ej02_AndresIzquierdoContext> options)
            : base(options)
        {
        }

        public DbSet<UT03_Ej02_AndresIzquierdo.Models.Game> Game { get; set; } = default!;

        public DbSet<UT03_Ej02_AndresIzquierdo.Models.Genre>? Genre { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Game>().HasData(
                new Game { GameId = 1, Title = "Last of us", GenreId = 1 },
                new Game { GameId = 2, Title = "Tomb Raider", GenreId = 1 },
                new Game { GameId = 3, Title = "Mario Bros", GenreId = 2 },
                new Game { GameId = 4, Title = "Rayman", GenreId = 2 },
                new Game { GameId = 5, Title = "Starcraft", GenreId = 3 }

            );
            modelBuilder.Entity<Genre>().HasData(
                 new Genre { IdGenre = 1, Name = "Adventure" },
                 new Genre { IdGenre = 2, Name = "Plataformer" },
                 new Genre { IdGenre = 3, Name = "Strategy" }
            );
        }
    }
}
