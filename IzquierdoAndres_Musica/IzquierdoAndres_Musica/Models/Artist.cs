using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace IzquierdoAndres_Musica.Models;

public partial class Artist
{
    public int ArtistId { get; set; }

    [DisplayName("Nombre")]
    [Required(ErrorMessage = "Obligatorio")]
    [MaxLength(50, ErrorMessage = "El nombre no puede tener más de 50 carácteres.")]
    [MinLength(1, ErrorMessage = "El nombre no puede tener menos de 1 carácter.")]
    public string? Name { get; set; }

    public virtual ICollection<Album> Albums { get; set; } = new List<Album>();
}
