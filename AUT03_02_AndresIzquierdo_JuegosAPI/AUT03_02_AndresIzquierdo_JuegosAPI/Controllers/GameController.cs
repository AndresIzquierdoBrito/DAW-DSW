using AUT03_02_AndresIzquierdo_JuegosAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace AUT03_02_AndresIzquierdo_JuegosAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GameController : ControllerBase
    {
        private static List<Game> Games = new List<Game>()
        {
            new Game { Id = 1, Name = "The Witcher 3: Wild Hunt", Genre = "Acción RPG" },
            new Game { Id = 2, Name = "Fortnite", Genre = "Battle Royale" },
            new Game { Id = 3, Name = "Grand Theft Auto V", Genre = "Acción-Aventura" },
            new Game { Id = 4, Name = "Minecraft", Genre = "Sandbox" },
            new Game { Id = 5, Name = "World of Warcraft", Genre = "MMO" }
        };

        // GET: api/<GameController>

        [HttpGet]
        public IActionResult Get()
        {
            return Ok(Games);
        }

        // GET api/<GameController>/5
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            
            return Ok(Games.Find(game => game.Id == id));
        }

        // POST api/<GameController>
        [HttpPost]
        public void IActionResult([FromBody] Game game)
        {
            if (game == null) { 
            
            }
        }

        // PUT api/<GameController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<GameController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
}
}
