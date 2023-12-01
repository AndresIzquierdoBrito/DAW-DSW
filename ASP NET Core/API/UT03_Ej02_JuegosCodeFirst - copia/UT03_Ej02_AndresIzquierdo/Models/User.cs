using System.Security.Claims;

namespace UT03_Ej02_AndresIzquierdo.Models
{
    public class User
    {
        public string Email { get; set; }

        public string Password { get; set; }

        public User(string email, string passwd)
        {
            this.Email = email;
            this.Password = passwd;
        }
    }
}
