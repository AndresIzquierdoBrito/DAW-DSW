using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using UT03_Ej02_AndresIzquierdo.Data;
using UT03_Ej02_AndresIzquierdo.Models;

namespace UT03_Ej02_AndresIzquierdo.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class GenresController : ControllerBase
    {
        private readonly UT03_Ej02_AndresIzquierdoContext _context;

        public GenresController(UT03_Ej02_AndresIzquierdoContext context)
        {
            _context = context;
        }

        // GET: api/Genres
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Genre>>> GetGenre()
        {
            if (_context.Genre == null)
            {
                return NotFound();
            }
            var genres = await _context.Genre.Select(g => new
            {
                g.IdGenre,
                g.Name,
                Games = g.GenreGames.Select(g => new
                {
                    g.GameId,
                    g.Title
                })
            })
                  .ToListAsync();
            return Ok(genres);
        }

        // GET: api/Genres/5
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [HttpGet("{id}")]
        public async Task<ActionResult<Genre>> GetGenre(int id)
        {
            if (_context.Genre == null)
            {
                return NotFound();
            }
            var genre = await _context.Genre.Select(g => new
            {
                g.IdGenre,
                g.Name,
                Games = g.GenreGames.Select(g => new
                {
                    g.GameId,
                    g.Title
                })
            }).FirstOrDefaultAsync(g => g.IdGenre == id);

            if (genre == null)
            {
                return NotFound();
            }

            return Ok(genre);
        }

        // PUT: api/Genres/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGenre(int id, GenreDTO genreDTO)
        {

            var genre = await _context.Genre.FindAsync(id);

            if (genre == null)
            {
                return NotFound($"No existe un género con el id {id}.");
            }

            genre.IdGenre = id;
            genre.Name = genreDTO.Name;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GenreExists(id))
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

        // POST: api/Genres
        [HttpPost]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public async Task<ActionResult<Genre>> PostGenre(GenreDTO genreDto)
        {
            if (_context.Genre == null)
            {
                return Problem("Entity set 'UT03_Ej02_AndresIzquierdoContext.Genre'  is null.");
            }

            var genre = new Genre
            {
                Name = genreDto.Name
            };
            _context.Genre.Add(genre);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetGenre", new { id = genre.IdGenre }, genre);
        }

        // DELETE: api/Genres/5
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGenre(int id)
        {
            if (_context.Genre == null)
            {
                return NotFound();
            }
            var genre = await _context.Genre.FindAsync(id);
            if (genre == null)
            {
                return NotFound();
            }

            _context.Genre.Remove(genre);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool GenreExists(int id)
        {
            return (_context.Genre?.Any(e => e.IdGenre == id)).GetValueOrDefault();
        }
    }
}
