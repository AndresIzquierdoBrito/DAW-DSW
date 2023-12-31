﻿// <auto-generated> This file has been auto generated by EF Core Power Tools. </auto-generated>
#nullable disable
using System;
using System.Collections.Generic;
using AUT02_05_DbFirst.Models;
using Microsoft.EntityFrameworkCore;

namespace AUT02_05_DbFirst.Data;

public partial class jardineriaContext : DbContext
{
    public jardineriaContext(DbContextOptions<jardineriaContext> options)
        : base(options)
    {
    }

    public virtual DbSet<gama_producto> gama_productos { get; set; }

    public virtual DbSet<producto> productos { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<gama_producto>(entity =>
        {
            entity.HasKey(e => e.gama).HasName("PK__gama_pro__4877EEE48B67C008");
        });

        modelBuilder.Entity<producto>(entity =>
        {
            entity.HasKey(e => e.codigo_producto).HasName("PK__producto__105107A9BE1AEBAD");

            entity.HasOne(d => d.gamaNavigation).WithMany(p => p.productos)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__producto__gama__3D5E1FD2");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}