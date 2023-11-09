using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IzquierdoAndres_Musica.Models
{
    [Table("Review")]
    public class Review
    {
        [Key]
        public int ReviewId { get; set; }

        [ForeignKey("ArtistId")]
        public int ArtistId { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [StringLength(50, MinimumLength = 1, ErrorMessage = "Introduce un título entre 1 y 50 carácteres.")]
        [DisplayName("Título")]
        public string? Title { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [StringLength(160, MinimumLength = 1, ErrorMessage = "Introduce un comentario entre 1 y 120 carácteres.")]
        [DisplayName("Comentario")]
        public string? Comment { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [Range(1, 5, ErrorMessage = "La valoración debe estar entre {1} y {2}.")]
        [DisplayName("Valoración")]
        public int Rating { get; set; }

        
    }
}
