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
            new Game ( "The Witcher 3: Wild Hunt", "Acción RPG" ),
            new Game ("Fortnite", "Battle Royale"),
            new Game ("Grand Theft Auto V", "Acción-Aventura"),
            new Game ("Minecraft", "Sandbox"),
            new Game ("World of Warcraft", "MMO")
        };

        // GET: api/<GameController>

        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpGet]
        public IActionResult Get()
        {
            return Ok(Games);
        }

        // GET api/<GameController>/5
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var game = Games.Find(game => game.Id == id);
            if (game == null) 
                return NotFound();
            return Ok(game);
        }

        // POST api/<GameController>
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [HttpPost]
        public void IActionResult([FromBody] Game game)
        {
            
        }

        // PUT api/<GameController>/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Game game)
        {
        }

        // DELETE api/<GameController>/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
}
}
