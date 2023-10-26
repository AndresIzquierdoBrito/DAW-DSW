﻿using System;
using System.Collections.Generic;

namespace IzquierdoAndres_Musica.Models;

public partial class Artist
{
    public int ArtistId { get; set; }

    public string? Name { get; set; }

    public virtual ICollection<Album> Albums { get; set; } = new List<Album>();
}
