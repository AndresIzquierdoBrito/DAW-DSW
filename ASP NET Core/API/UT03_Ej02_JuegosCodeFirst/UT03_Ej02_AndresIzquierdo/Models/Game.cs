using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace UT03_Ej02_AndresIzquierdo.Models
{
    public class Game
    {
        [Key]
        public int GameId { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [StringLength(12, MinimumLength = 5, ErrorMessage = "Introduce un título entre 5 y 12 carácteres.")]
        public string Title { get; set; }

        public int GenreId { get; set; }

        public Genre? Genre { get; set; }
    }

    
}
