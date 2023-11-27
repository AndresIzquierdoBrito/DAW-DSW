using System.ComponentModel.DataAnnotations;

namespace AUT03_05_AndresIzquierdo_MusicaAPI.Models
{
    public class AlbumDTO
    {
        [Required(ErrorMessage = "Título (title): Campo obligatorio.")]
        [StringLength(160, MinimumLength = 1, ErrorMessage = "Título (title): Introduce un título de entre 1 y 160 carácteres.")]
        public string Title { get; set; }

        [Required(ErrorMessage = "Campo obligatorio.")]
        public int ArtistId { get; set; }
    }
}
