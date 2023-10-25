using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace IzquierdoAndres_Musica.Models
{
    public class Review
    {

        public string? Title { get; set; }

        public string? Comment { get; set; }

        public int Rating { get; set; }

        public virtual Album Album { get; set; }

    }
}
