﻿// <auto-generated />
using System;
using IzquierdoAndres_Musica.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

#nullable disable

namespace IzquierdoAndres_Musica.Migrations
{
    [DbContext(typeof(LocalDBChinookContext))]
    partial class LocalDBChinookContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.13")
                .HasAnnotation("Relational:MaxIdentifierLength", 128);

            SqlServerModelBuilderExtensions.UseIdentityColumns(modelBuilder);

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Album", b =>
                {
                    b.Property<int>("AlbumId")
                        .HasColumnType("int");

                    b.Property<int>("ArtistId")
                        .HasColumnType("int");

                    b.Property<string>("Title")
                        .IsRequired()
                        .HasMaxLength(160)
                        .HasColumnType("nvarchar(160)");

                    b.HasKey("AlbumId");

                    b.HasIndex(new[] { "ArtistId" }, "IFK_AlbumArtistId");

                    b.ToTable("Album");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Artist", b =>
                {
                    b.Property<int>("ArtistId")
                        .HasColumnType("int");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasMaxLength(120)
                        .HasColumnType("nvarchar(120)");

                    b.HasKey("ArtistId");

                    b.ToTable("Artist");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.InvoiceLine", b =>
                {
                    b.Property<int>("InvoiceLineId")
                        .HasColumnType("int");

                    b.Property<int>("InvoiceId")
                        .HasColumnType("int");

                    b.Property<int>("Quantity")
                        .HasColumnType("int");

                    b.Property<int>("TrackId")
                        .HasColumnType("int");

                    b.Property<decimal>("UnitPrice")
                        .HasColumnType("numeric(10, 2)");

                    b.HasKey("InvoiceLineId");

                    b.HasIndex(new[] { "InvoiceId" }, "IFK_InvoiceLineInvoiceId");

                    b.HasIndex(new[] { "TrackId" }, "IFK_InvoiceLineTrackId");

                    b.ToTable("InvoiceLine");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.PlaylistTrack", b =>
                {
                    b.Property<int>("PlaylistId")
                        .HasColumnType("int");

                    b.Property<int>("TrackId")
                        .HasColumnType("int");

                    b.HasKey("PlaylistId", "TrackId");

                    SqlServerKeyBuilderExtensions.IsClustered(b.HasKey("PlaylistId", "TrackId"), false);

                    b.HasIndex(new[] { "TrackId" }, "IFK_PlaylistTrackTrackId");

                    b.ToTable("PlaylistTrack");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Review", b =>
                {
                    b.Property<int>("ReviewId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("ReviewId"));

                    b.Property<int>("ArtistId")
                        .HasColumnType("int");

                    b.Property<string>("Comment")
                        .IsRequired()
                        .HasMaxLength(120)
                        .HasColumnType("nvarchar(120)");

                    b.Property<int>("Rating")
                        .HasColumnType("int");

                    b.Property<string>("Title")
                        .IsRequired()
                        .HasMaxLength(50)
                        .HasColumnType("nvarchar(50)");

                    b.HasKey("ReviewId");

                    b.HasIndex("ArtistId");

                    b.ToTable("Review");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Track", b =>
                {
                    b.Property<int>("TrackId")
                        .HasColumnType("int");

                    b.Property<int?>("AlbumId")
                        .HasColumnType("int");

                    b.Property<int?>("Bytes")
                        .HasColumnType("int");

                    b.Property<string>("Composer")
                        .IsRequired()
                        .HasMaxLength(220)
                        .HasColumnType("nvarchar(220)");

                    b.Property<int?>("GenreId")
                        .HasColumnType("int");

                    b.Property<int>("MediaTypeId")
                        .HasColumnType("int");

                    b.Property<int>("Milliseconds")
                        .HasColumnType("int");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasMaxLength(200)
                        .HasColumnType("nvarchar(200)");

                    b.Property<decimal>("UnitPrice")
                        .HasColumnType("numeric(10, 2)");

                    b.HasKey("TrackId");

                    b.HasIndex(new[] { "AlbumId" }, "IFK_TrackAlbumId");

                    b.HasIndex(new[] { "GenreId" }, "IFK_TrackGenreId");

                    b.HasIndex(new[] { "MediaTypeId" }, "IFK_TrackMediaTypeId");

                    b.ToTable("Track");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Album", b =>
                {
                    b.HasOne("IzquierdoAndres_Musica.Models.Artist", "Artist")
                        .WithMany("Albums")
                        .HasForeignKey("ArtistId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired()
                        .HasConstraintName("FK_AlbumArtistId");

                    b.Navigation("Artist");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.InvoiceLine", b =>
                {
                    b.HasOne("IzquierdoAndres_Musica.Models.Track", "Track")
                        .WithMany("InvoiceLines")
                        .HasForeignKey("TrackId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired()
                        .HasConstraintName("FK_InvoiceLineTrackId");

                    b.Navigation("Track");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.PlaylistTrack", b =>
                {
                    b.HasOne("IzquierdoAndres_Musica.Models.Track", "Track")
                        .WithMany("PlaylistTracks")
                        .HasForeignKey("TrackId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired()
                        .HasConstraintName("FK_PlaylistTrackTrackId");

                    b.Navigation("Track");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Review", b =>
                {
                    b.HasOne("IzquierdoAndres_Musica.Models.Artist", null)
                        .WithMany("Reviews")
                        .HasForeignKey("ArtistId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Track", b =>
                {
                    b.HasOne("IzquierdoAndres_Musica.Models.Album", "Album")
                        .WithMany("Tracks")
                        .HasForeignKey("AlbumId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .HasConstraintName("FK_TrackAlbumId");

                    b.Navigation("Album");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Album", b =>
                {
                    b.Navigation("Tracks");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Artist", b =>
                {
                    b.Navigation("Albums");

                    b.Navigation("Reviews");
                });

            modelBuilder.Entity("IzquierdoAndres_Musica.Models.Track", b =>
                {
                    b.Navigation("InvoiceLines");

                    b.Navigation("PlaylistTracks");
                });
#pragma warning restore 612, 618
        }
    }
}
