﻿// <auto-generated> This file has been auto generated by EF Core Power Tools. </auto-generated>
#nullable disable
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace IzquierdoAndres_MusicaMVC.Models;

[PrimaryKey("PlaylistId", "TrackId")]
[Table("PlaylistTrack")]
[Index("TrackId", Name = "IFK_PlaylistTrackTrackId")]
public partial class PlaylistTrack
{
    [Key]
    public int PlaylistId { get; set; }

    [Key]
    public int TrackId { get; set; }

    [ForeignKey("TrackId")]
    [InverseProperty("PlaylistTracks")]
    public virtual Track Track { get; set; }
}