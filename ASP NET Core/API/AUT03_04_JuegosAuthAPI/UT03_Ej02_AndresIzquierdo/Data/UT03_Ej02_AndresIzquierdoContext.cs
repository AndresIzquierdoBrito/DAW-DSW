using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using AUT03_04_AndresIzquierdo_JuegosAuthAPI.Models;

namespace AUT03_04_AndresIzquierdo_JuegosAuthAPI.Data
{
    public class UT03_Ej02_AndresIzquierdoContext : DbContext
    {
        public UT03_Ej02_AndresIzquierdoContext (DbContextOptions<UT03_Ej02_AndresIzquierdoContext> options)
            : base(options)
        {
        }

        public DbSet<AUT03_04_AndresIzquierdo_JuegosAuthAPI.Models.Game> Game { get; set; } = default!;

        public DbSet<AUT03_04_AndresIzquierdo_JuegosAuthAPI.Models.Genre>? Genre { get; set; }

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
