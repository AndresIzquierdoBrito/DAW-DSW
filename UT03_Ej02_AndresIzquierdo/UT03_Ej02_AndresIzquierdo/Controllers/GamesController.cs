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
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Game>>> GetGame()
        {
            if (_context.Game == null)
            {
                return NotFound();
            }
            var games = await _context.Game.Select(g => new
            {
                GameId = g.GameId,
                Title = g.Title,
                Genre = new
                {
                    GenreId = g.GenreId,
                    Name = g.Genre.Name
                }
            }).ToListAsync();

            return Ok(games);

        }

        // GET: api/Games/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Game>> GetGame(int id)
        {
            if (_context.Game == null)
            {
                return NotFound();
            }
            var game = await _context.Game
               .Where(g => g.GameId == id)
               .Select(g => new
               {
                   GameId = g.GameId,
                   Title = g.Title,
                   Genre = new
                   {
                       GenreId = g.GenreId,
                       Name = g.Genre.Name
                   }
               })
               .FirstOrDefaultAsync();
            if (game == null)
            {
                return NotFound();
            }

            return Ok(game);
        }

        // PUT: api/Games/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGame(int id, Game game)
        {
            if (id != game.GameId)
            {
                return BadRequest();
            }

            _context.Entry(game).State = EntityState.Modified;

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

            return CreatedAtAction("Get", game);
        }

        // POST: api/Games
        [HttpPost]
        public async Task<ActionResult<Game>> PostGame(Game game)
        {
            if (_context.Game == null)
            {
                return Problem("Entity set 'UT03_Ej02_AndresIzquierdoContext.Game'  is null.");
            }
            _context.Game.Add(game);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetGame", new { id = game.GameId }, game);
        }
        //https://www.phind.com/search?cache=ch8ju5t9torvy6c7sa3fevmy
        // DELETE: api/Games/5
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
