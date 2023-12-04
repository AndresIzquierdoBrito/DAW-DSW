using System.ComponentModel.DataAnnotations;

namespace AUT03_04_AndresIzquierdo_JuegosAuthAPI.Models
{
    public class GenreDTO
    {
        [Required(ErrorMessage = "Campo olbitario.")]
        [StringLength(12, MinimumLength = 2, ErrorMessage = "Introduce un género entre 2 y 12 carácteres.")]
        public string Name { get; set; }
    }
}
