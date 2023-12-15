using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using AUT03_06_IzquierdoAndres_AuthMusicaAPI.Data;
using AUT03_06_IzquierdoAndres_AuthMusicaAPI.Models;
using Microsoft.AspNetCore.Authorization;

namespace AUT03_06_IzquierdoAndres_AuthMusicaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class ArtistsController : ControllerBase
    {
        private readonly ChinookContext _context;

        // Constructor para el ArtistController. Inyecta una instancia de ChinookContext, contexto de la base de datos para
        // interactuar a traves de Entity Framework.
        public ArtistsController(ChinookContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Recupera una lista de los artistas ordenados por nombre.
        /// Incluye información sobre los álbumes y el número de pistas en cada álbum.
        /// </summary>
        /// <returns>Lista de artistas con información asociada.</returns>
        // GET: api/Artists
        [HttpGet]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<IEnumerable<Artist>>> GetArtists()
        {
            try // Evita excepciones en caso de que la DB no exista.
            {
                var artists = await _context.Artists
                    .OrderBy(n => n.Name)
                    .Select(a => new
                    {
                        a.ArtistId,
                        a.Name,
                        Albums = a.Albums.Select(b => new
                        {
                            b.AlbumId,
                            b.Title,
                            NoTracks = b.Tracks.Count
                        })
                    })
                    .Take(10)
                    .ToListAsync();

                return Ok(artists);
            }
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }

        /// <summary>
        /// Recupera información detallada sobre un artista específico mediante su ID.
        /// Incluye información sobre los álbumes y el número de pistas en cada álbum.
        /// </summary>
        /// <param name="id">El ID del artista a recuperar.</param>
        /// <returns>Información detallada sobre el artista especificado.</returns>
        // GET: api/Artists/5
        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<Artist>> GetArtist(int id)
        {
            try
            {
                var artist = await _context.Artists.Select(a => new
                {
                    a.ArtistId,
                    a.Name,
                    Albums = a.Albums.Select(b => new
                    {
                        b.AlbumId,
                        b.Title,
                        NoTracks = b.Tracks.Count
                    })

                }).FirstOrDefaultAsync(a => a.ArtistId == id);

                if (artist == null)
                {
                    ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status404NotFound, "No se ha encontrado", $"No se ha encontrado un álbum con el id especificado: {id}.");
                    return NotFound(problemDetails);
                }

                return Ok(artist);
            }
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }

        /// <summary>
        /// Actualiza el nombre de un artista existente según el ID proporcionado.
        /// </summary>
        /// <param name="id">El ID del artista a actualizar.</param>
        /// <param name="artistDTO">DTO que contiene el nombre actualizado del artista.</param>
        /// <returns>Sin contenido si la actualización es exitosa.</returns>
        // PUT: api/Artists/5
        [HttpPut("{id}")]
        [Authorize(Roles = "Admin,Manager")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status403Forbidden)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> PutArtist(int id, ArtistDTO artistDTO)
        {
            try
            {
                var artist = await _context.Artists.FindAsync(id);

                if (artist == null)
                {
                    ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status404NotFound, "No se ha encontrado", $"No se ha encontrado un artista con el id especificado: {id}.");
                    return NotFound(problemDetails);
                }

                artist.Name = artistDTO.Name;

                try
                {
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ArtistExists(id))
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
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }

        /// <summary>
        /// Crea un nuevo artista con el nombre proporcionado.
        /// </summary>
        /// <param name="artistDTO">DTO que contiene el nombre del nuevo artista.</param>
        /// <returns>Artista creado con un código 201 (Creado) y una ubicación que apunta al nuevo recurso.</returns>
        // POST: api/Artists
        [HttpPost]
        [Authorize(Roles = "Admin,Manager")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status403Forbidden)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status409Conflict)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<Artist>> PostArtist(ArtistDTO artistDTO)
        {
            try
            {
                int id = _context.Artists.OrderBy(a => a.ArtistId).Last().ArtistId + 1;

                var artist = new Artist
                {
                    ArtistId = id,
                    Name = artistDTO.Name
                };

                _context.Artists.Add(artist);

                try
                {
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateException)
                {
                    if (ArtistExists(artist.ArtistId))
                    {
                        ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status409Conflict, "Conflicto", $"Se ha encontrado un conflicto en el procesamiento de la petición.");
                        return Conflict(problemDetails);
                    }
                    else
                    {
                        throw;
                    }
                }

                return CreatedAtAction("GetArtist", new { id = artist.ArtistId }, artist);
            }
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }

        /// <summary>
        /// Elimina un artista específico mediante su ID.
        /// </summary>
        /// <param name="id">El ID del artista a eliminar.</param>
        /// <returns>Sin contenido si la eliminación es exitosa.</returns>
        // DELETE: api/Artists/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "Admin,Manager")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status403Forbidden)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> DeleteArtist(int id)
        {
            try
            {
                var artist = await _context.Artists.Include(a => a.Albums).ThenInclude(t => t.Tracks).FirstOrDefaultAsync(a => a.ArtistId == id);

                if (artist == null)
                {
                    ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status404NotFound, "Not Found", $"No se ha encontrado un artista con el id especificado: {id}.");
                    return NotFound(problemDetails);
                }

                _context.Artists.Remove(artist);
                await _context.SaveChangesAsync();

                return NoContent();
            }
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }

        /// <summary>
        /// Genera un objeto ProblemDetails, código de estado, título y descripción, a través de los parametros.
        /// </summary>
        /// <param name="id">Código del estado, título y descripción.</param>
        /// <returns>Objeto ProblemDetails con la información proporcionada.</returns>
        private ProblemDetails CreateProblemDetails(int statusCode, string title, string detail)
        {
            return new ProblemDetails
            {
                Status = statusCode,
                Title = title,
                Detail = detail
            };
        }

        /// <summary>
        /// Verifica si un artista con el ID especificado existe en la base de datos.
        /// </summary>
        /// <param name="id">El ID del artista a verificar.</param>
        /// <returns>True si el artista existe; de lo contrario, false.</returns>
        private bool ArtistExists(int id)
        {
            return (_context.Artists?.Any(e => e.ArtistId == id)).GetValueOrDefault();
        }
    }
}
