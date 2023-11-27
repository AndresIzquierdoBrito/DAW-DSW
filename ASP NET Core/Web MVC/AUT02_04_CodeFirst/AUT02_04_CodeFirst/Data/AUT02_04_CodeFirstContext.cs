using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using AUT02_04_CodeFirst.Models;

namespace AUT02_04_CodeFirst.Data
{
    public class AUT02_04_CodeFirstContext : DbContext
    {
        public AUT02_04_CodeFirstContext (DbContextOptions<AUT02_04_CodeFirstContext> options)
            : base(options)
        {
        }

        public DbSet<AUT02_04_CodeFirst.Models.Recipe> Recipe { get; set; } = default!;
    }
}
