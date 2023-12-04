using System.ComponentModel.DataAnnotations;

namespace AUT03_04_AndresIzquierdo_JuegosAuthAPI.Models
{
    public class GameDTO
    {
        [Required(ErrorMessage = "Campo obligatorio.")]
        [StringLength(12, MinimumLength = 5, ErrorMessage = "Introduce un título entre 5 y 12 carácteres.")]
        public string Title { get; set; }
        public int GenreId { get; set; }
    }
}
