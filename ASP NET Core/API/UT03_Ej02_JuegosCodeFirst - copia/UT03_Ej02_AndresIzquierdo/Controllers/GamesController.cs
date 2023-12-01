using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using UT03_Ej02_AndresIzquierdo.Data;
using UT03_Ej02_AndresIzquierdo.Models;

namespace UT03_Ej02_AndresIzquierdo.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GamesController : ControllerBase
    {
        private readonly UT03_Ej02_AndresIzquierdoContext _context;

        public GamesController(UT03_Ej02_AndresIzquierdoContext context)
        {
            _context = context;
        }

        // GET: api/Games
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Game>>> GetGame()
        {
            if (_context.Game == null)
            {
                return NotFound();
            }
            var games = await _context.Game.Select(g => new
            {
                g.GameId,
                 g.Title,
                Genre = new
                {
                    g.GenreId,
                    g.Genre.Name
                }
            }).ToListAsync();

            return Ok(games);

        }

        // GET: api/Games/5
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [HttpGet("{id}")]
        public async Task<ActionResult<Game>> GetGame(int id)
        {
            if (_context.Game == null)
            {
                return NotFound();
            }
            var game = await _context.Game.Include(g => g.Genre)
               .Select(g => new
               {
                   g.GameId,
                   g.Title,
                   Genre = new
                   {
                       g.Genre.IdGenre,
                       g.Genre.Name
                   }
               })
               .FirstOrDefaultAsync(g => g.GameId == id);

            if (game == null)
            {
                return NotFound();
            }

            return Ok(game);
        }

        // PUT: api/Games/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGame(int id, GameDTO gameDTO)
        {

            var game = await _context.Game.FindAsync(id);

            if (game == null) 
            { 
                return NotFound($"No existe un juego on cel id {id}"); 
            }

            var genresId = _context.Genre.Select(g => g.IdGenre);

            if (!genresId.Contains(gameDTO.GenreId))
            {
                return NotFound("El genero no existe");
            }

            game.Title = gameDTO.Title;
            game.GenreId = gameDTO.GenreId;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GameExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Games
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [HttpPost]
        public async Task<ActionResult<Game>> PostGame(GameDTO gameDto)
        {
            if (_context.Game == null)
            {
                return Problem("Entity set 'UT03_Ej02_AndresIzquierdoContext.Game'  is null.");
            }

            var game = new Game
            {
                Title = gameDto.Title,
                GenreId = gameDto.GenreId
            };
            _context.Game.Add(game);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetGame", new { id = game.GameId }, game);
            // Comentarselo al profe
        }

        // DELETE: api/Games/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGame(int id)
        {
            if (_context.Game == null)
            {
                return NotFound();
            }
            var game = await _context.Game.FindAsync(id);
            if (game == null)
            {
                return NotFound();
            }

            _context.Game.Remove(game);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool GameExists(int id)
        {
            return (_context.Game?.Any(e => e.GameId == id)).GetValueOrDefault();
        }
    }
}
