namespace AUT03_02_AndresIzquierdo_JuegosAPI.Models
{
    public class Game
    {
        private static int globalGameID = 0;

        public int Id { get; private set; }

        public string Name { get; set; }

        public string Genre { get; set; }

        public Game(string Name, string Genre)
        {
            this.Name = Name;
            this.Genre = Genre;
            this.Id = Interlocked.Increment(ref globalGameID);
        }
    }
}
