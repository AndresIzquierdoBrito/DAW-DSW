using System;
using System.Collections.Generic;

namespace IzquierdoAndres_Musica.Models;

public partial class PlaylistTrack
{
    public int PlaylistId { get; set; }

    public int TrackId { get; set; }

    public virtual Track Track { get; set; } = null!;
}
