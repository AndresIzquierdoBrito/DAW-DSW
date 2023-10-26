﻿// <auto-generated> This file has been auto generated by EF Core Power Tools. </auto-generated>
#nullable disable
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace IzquierdoAndres_Musica.Models;

[Table("Album")]
[Index("ArtistId", Name = "IFK_AlbumArtistId")]
public partial class Album
{
    [Key]
    public int AlbumId { get; set; }

    [Required(ErrorMessage = "Campo olbitario.")]
    [StringLength(160)]
    [DisplayName("Título")]
    public string Title { get; set; }

    public int ArtistId { get; set; }

    [DisplayName("Artista")]
    [ForeignKey("ArtistId")]
    [InverseProperty("Albums")]
    public virtual Artist Artist { get; set; }

    [InverseProperty("Album")]
    public virtual ICollection<Track> Tracks { get; set; } = new List<Track>();
}