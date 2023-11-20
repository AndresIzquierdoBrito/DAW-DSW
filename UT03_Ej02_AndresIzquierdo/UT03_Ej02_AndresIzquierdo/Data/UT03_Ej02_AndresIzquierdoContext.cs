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
    }
}
