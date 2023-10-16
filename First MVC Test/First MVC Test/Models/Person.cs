using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace First_MVC_Test.Models
{
    public class Person
    {
        public int Id { get; set; }

        [DisplayName("Nombre")]
        public string Name { get; set; }

        [DisplayName("Edad")]
        public int Age { get; set; }

        [EmailAddress]
        [DisplayName("Correo electrónico")]
        public string? Email { get; set; }
    }
}
