using System.ComponentModel.DataAnnotations;

namespace AUT03_05_AndresIzquierdo_MusicaAPI.Models
{
    public class ArtistDTO
    {
        [Required(ErrorMessage = "Nombre (name): Campo obligatorio.")]
        [StringLength(120, MinimumLength = 1, ErrorMessage = "Nombre (name): Introduce un nombre de entre 1 y 120 carácteres.")]
        public string Name { get; set; }
    }
}
