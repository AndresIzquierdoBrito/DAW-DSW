using System.ComponentModel;

namespace AUT02_02_IzquierdoAndres_ModernFamily.Models
{
    public class Personaje
    {
        public int Id { get; set; }

        [DisplayName ("Nombre")]
        public string Name { get; set; }

        [DisplayName("Familia")]
        public string Family { get; set; }

        [DisplayName("Número de hijos")]
        public int NChildren { get; set; }
    }
}
