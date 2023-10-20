using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace AUT02_04_CodeFirst.Models
{
    public class Recipe
    {
        [Key]
        public int Id { get; set; }

        [DisplayName("Nombre")]
        [Required(ErrorMessage = "Obligatorio")]
        [MaxLength(50, ErrorMessage = "El nombre no puede tener más de 50 carácteres.")]
        [MinLength(3, ErrorMessage = "El nombre no puede tener menos de 3 carácteres.")]
        public string Name { get; set; }

        [DisplayName("Descripción")]
        [Required(ErrorMessage = "Obligatorio")]
        [MaxLength(50, ErrorMessage = "El nombre no puede tener más de 50 carácteres.")]
        [MinLength(3, ErrorMessage = "El nombre no puede tener menos de 3 carácteres.")]
        public string Description { get; set; }

        [DisplayName("Cantidad de KCal")]
        [Range(1, 10000, ErrorMessage = "La cantidad de KCal ha de estar entre 1 y 10000")]
        [Required(ErrorMessage = "Obligatorio")]
        public int KCalAmount { get; set; }

        [DisplayName("Ingredientes")]
        [Required(ErrorMessage = "Obligatorio")]
        [MaxLength(50, ErrorMessage = "La lista de ingredientes no puede tener más de 1000 carácteres.")]
        [MinLength(1, ErrorMessage = "El nombre no puede tener menos de 1 carácter.")]
        public string Ingredients { get; set; }

    }
}
