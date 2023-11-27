using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace AUT02_02_IzquierdoAndres_ModernFamily.Models
{
    public class Personaje
    {
        public int Id { get; set; }

        [Required(ErrorMessage = "Requerido")]
        [StringLength (30, MinimumLength = 3, ErrorMessage = "Introduce un nombre entre 3 y 30 carácteres.")]
        [DisplayName ("Nombre")]
        public string Name { get; set; }

        [Required(ErrorMessage = "Required")]
        [DisplayName("Familia")]
        public string Family { get; set; }

        [Required(ErrorMessage = "Required")]

        [DisplayName("Número de hijos")]
        public int NChildren { get; set; }
    }
}
