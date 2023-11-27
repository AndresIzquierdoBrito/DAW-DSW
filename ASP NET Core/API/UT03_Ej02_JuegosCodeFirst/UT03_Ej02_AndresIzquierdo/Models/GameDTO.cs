using System.ComponentModel.DataAnnotations;

namespace UT03_Ej02_AndresIzquierdo.Models
{
    public class GameDTO
    {
        [Required(ErrorMessage = "Campo obligatorio.")]
        [StringLength(12, MinimumLength = 5, ErrorMessage = "Introduce un título entre 5 y 12 carácteres.")]
        public string Title { get; set; }
        public int GenreId { get; set; }
    }
}
