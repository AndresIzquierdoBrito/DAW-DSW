using AUT03_02_AndresIzquierdo_JuegosAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace AUT03_02_AndresIzquierdo_JuegosAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GameController : ControllerBase
    {
        // GET: api/<GameController>
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpGet]
        public IActionResult Get()
        {
            return Ok(Game.gameList);
        }

        // GET api/<GameController>/5
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var game = Game.gameList.Find(game => game.Id == id);
            if (game == null) 
                return NotFound();
            return Ok(game);
        }

        // POST api/<GameController>
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [HttpPost]
        public IActionResult Post([FromBody] Game game)
        {
            Game.gameList.Add(game);

            return CreatedAtAction("Get", game);
        }

        // PUT api/<GameController>/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpPut("{id}")]
        public IActionResult Put(int id, [FromBody] Game game)
        {
            Game.ReduceGlobalIdCount(); // Necesario ya que al hacer binding, se crea un juego y aumenta el count de ID global.
            int index = Game.gameList.FindIndex(x => x.Id == id);

            if (index == -1)
            {
                return NotFound($"No existe un juego con el id {id}.");
            }

            Game.gameList[index].Name = game.Name;
            Game.gameList[index].Genre = game.Genre;

            return Ok(Game.gameList[index]);

        }

        // DELETE api/<GameController>/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            int index = Game.gameList.FindIndex(x => x.Id == id);

            if (index == -1)
            {
                return NotFound($"No existe un juego con el id {id}.");
            }

            var deletedGame = Game.gameList.Find(game => game.Id == id);
            Game.gameList.RemoveAt(index);

            return Ok($"Borrado con exito el juego con ID: {id}. Nombre: {deletedGame.Name} / Género: {deletedGame.Genre}");
        }
    }
}
