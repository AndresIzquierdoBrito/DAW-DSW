using System.Security.Claims;
using System.Security.Principal;

namespace AUT03_04_JuegosAPIAuth.Models
{
    public class User
    {
        private static int globalUserID = 0;

        public ClaimsIdentity Id { get; private set; }

        public string UserName { get; set; }

        public string Email { get; set; }

        public User(string userName, string email)
        {
            this.UserName = userName;
            this.Email = email;
        }
    }
}
