using System.ComponentModel.DataAnnotations;
using System.ComponentModel;

namespace AUT03_04_JuegosAPIAuth.Models
{
    public class Game
    {
        private static int globalGameID = 0;

        public int Id { get; private set; }

        [DisplayName("Nombre")]
        [MinLength(5, ErrorMessage = "El nómbre debe tener 5 carácteres como mínimo.")]
        [MaxLength(15, ErrorMessage = "El nómbre debe tener 15 carácteres como máximo.")]
        public string Name { get; set; }

        [DisplayName("Género")]
        [MinLength(2, ErrorMessage = "El nómbre debe tener 2 carácteres como mínimo.")]
        [MaxLength(12, ErrorMessage = "El nómbre debe tener 12 carácteres como máximo.")] public string Genre { get; set; }

        public Game(string Name, string Genre)
        {
            this.Name = Name;
            this.Genre = Genre;
            Id = Interlocked.Increment(ref globalGameID);
        }
        public static void ReduceGlobalIdCount() => globalGameID--;


        // Inicializo la lista aqui para evitar que alguno de los metodos CRUD creen un juego sin previamente tener creada una lista base de juegos.
        public static List<Game> gameList = new List<Game>()
        {
            new Game ("The Witcher 3: Wild Hunt", "Acción RPG" ),
            new Game ("Minecraft", "Sandbox"),
            new Game ("World of Warcraft", "MMO")
        };
    }
}
