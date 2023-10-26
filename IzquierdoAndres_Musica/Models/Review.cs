using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel;

namespace IzquierdoAndres_Musica.Models
{
    [Table("Review")]
    public class Review
    {
        [Key]
        public int ReviewId { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [StringLength(50)]
        [DisplayName("Título")]
        public string? Title { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [StringLength(120)]
        [DisplayName("Comentario")]
        public string? Description { get; set; }

        [Required(ErrorMessage = "Campo olbitario.")]
        [Range(1, 5, ErrorMessage = "La valoración debe estar entre {1} y {2}.")]
        [DisplayName("Valoración")]
        public int Rating { get; set; }

        [ForeignKey("ArtistId")]
        public virtual Artist Artist { get; set; }
    }
}
