using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace UT03_Ej02_AndresIzquierdo.Models
{
    public class Genre
    {
        [Key]
        public int IdGenre { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [StringLength(12, MinimumLength = 2, ErrorMessage = "Introduce un género entre 2 y 12 carácteres.")]
        public string Name { get; set; }

        public List<Game>? GenreGames{ get; set; }

    }
}
