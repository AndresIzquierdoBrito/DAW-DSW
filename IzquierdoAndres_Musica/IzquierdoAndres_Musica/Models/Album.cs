using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace IzquierdoAndres_Musica.Models;

public partial class Album
{
    [Key]
    public int AlbumId { get; set; }

    [DisplayName("Título")]
    [Required(ErrorMessage = "Obligatorio")]
    [MaxLength(50, ErrorMessage = "El título no puede tener más de 50 carácteres.")]
    [MinLength(1, ErrorMessage = "El título no puede tener menos de 3 carácteres.")]
    public string Title { get; set; } = null!;

    public int ArtistId { get; set; }

    [DisplayName("Artista")]
    public virtual Artist Artist { get; set; } = null!;
}
