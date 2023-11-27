using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using AUT03_05_AndresIzquierdo_MusicaAPI.Data;
using AUT03_05_AndresIzquierdo_MusicaAPI.Models;

namespace AUT03_05_AndresIzquierdo_MusicaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AlbumsController : ControllerBase
    {
        private readonly ChinookContext _context;

        // Constructor para el AlbumsController. Inyecta una instancia de ChinookContext, contexto de la base de datos para
        // interactuar a traves de Entity Framework.
        public AlbumsController(ChinookContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Recupera una lista paginada de los últimos 10 álbumes ordenados por título.
        /// Incluye información sobre el artista, el número de pistas y detalles de cada pista.
        /// </summary>
        /// <returns>Lista de álbumes con la información asociada.</returns>
        // GET: api/Albums
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Album>>> GetAlbums()
        {
            try // Evita excepciones en caso de que la DB no exista.
            {
                var albums = await _context.Albums
                .Include(a => a.Artist)
                .OrderByDescending(a => a.Title)
                .Select(a => new
                {
                    a.AlbumId,
                    a.Title,
                    Artist = new
                    {
                        a.ArtistId,
                        a.Artist.Name
                    },
                    NoTracks = a.Tracks.Count,
                    Tracks = a.Tracks.Select(t => new
                    {
                        t.TrackId,
                        t.Name,
                        Duration = TimeSpan.FromMilliseconds(t.Milliseconds).ToString(@"m\:ss"),
                        Size = $"{(double)t.Bytes / (1024 * 1024):F2} MB"
                    })
                })
                .Take(10)
                .ToListAsync();

                return Ok(albums);
            }
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }

        /// <summary>
        /// Recupera información detallada sobre un álbum específico mediante su ID.
        /// Incluye información sobre el artista y detalles de cada pista.
        /// </summary>
        /// <param name="id">El ID del álbum a recuperar.</param>
        /// <returns>Información detallada sobre el álbum especificado.</returns>
        // GET: api/Albums/5
        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Album>> GetAlbum(int id)
        {
            try
            {
                var album = await _context.Albums.Include(a => a.Artist).Select(a => new
                {
                    a.AlbumId,
                    a.Title,
                    Artist = new
                    {
                        a.ArtistId,
                        a.Artist.Name
                    },
                    NoTracks = a.Tracks.Count,
                    Tracks = a.Tracks.Select(t => new
                    {
                        t.TrackId,
                        t.Name,
                        Duration = TimeSpan.FromMilliseconds(t.Milliseconds).ToString(@"m\:ss"),
                        Size = $"{(double)t.Bytes / (1024 * 1024):F2} MB"
                    })
                }).FirstOrDefaultAsync(a => a.AlbumId == id);

                if (album == null)
                {
                    ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status404NotFound, "No se ha encontrado", $"No se ha encontrado un álbum con el id especificado: {id}.");
                    return NotFound(problemDetails);
                }

                return Ok(album);
            }
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }


        /// <summary>
        /// Actualiza la información de un álbum existente según el ID proporcionado.
        /// </summary>
        /// <param name="id">El ID del álbum a actualizar.</param>
        /// <param name="albumDTO">DTO que contiene la información actualizada del álbum.</param>
        /// <returns>Sin contenido si la actualización es exitosa.</returns>
        // PUT: api/Albums/5
        [HttpPut("{id}")]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status409Conflict)]
        public async Task<IActionResult> PutAlbum(int id, AlbumDTO albumDTO)
        {
            try
            {
                var album = await _context.Albums.FindAsync(id);

                if (album == null)
                {
                    ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status404NotFound, "No se ha encontrado", $"No se ha encontrado un álbum con el id especificado: {id}.");
                    return NotFound(problemDetails);
                };

                var artist = await _context.Artists.FindAsync(albumDTO.ArtistId);

                if (artist == null)
                {
                    ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status404NotFound, "No se ha encontrado", $"No se ha encontrado un artista con el id especificado: {id}.");
                    return NotFound(problemDetails);
                }

                album.Title = albumDTO.Title;
                album.ArtistId = albumDTO.ArtistId;

                try
                {
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!AlbumExists(id))
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
        /// Crea un nuevo álbum con la información proporcionada.
        /// </summary>
        /// <param name="albumDTO">DTO que contiene la información del nuevo álbum.</param>
        /// <returns>Álbum creado con un código 201 (Creado) y una ubicación que apunta al nuevo recurso.</returns>
        // POST: api/Albums
        [HttpPost]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status409Conflict)]
        public async Task<ActionResult<Album>> PostAlbum(AlbumDTO albumDTO)
        {
            try
            {
                int id = _context.Albums.OrderBy(a => a.AlbumId).Last().AlbumId + 1;
                var album = new Album
                {
                    AlbumId = id,
                    Title = albumDTO.Title,
                    ArtistId = albumDTO.ArtistId
                };
                _context.Albums.Add(album);
                try
                {
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateException)
                {
                    if (AlbumExists(album.AlbumId))
                    {
                        ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status409Conflict, "Conflicto", $"Se ha encontrado un conflicto en el procesamiento de la petición.");
                        return Conflict(problemDetails);
                    }
                    else
                    {
                        throw;
                    }
                }

                return CreatedAtAction("GetAlbum", new { id = album.AlbumId }, album);
            }
            catch
            {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "Un error ha ocurrido mientras se recuperaban los datos de la base de datos." });
            }
        }

        /// <summary>
        /// Elimina un álbum específico mediante su ID.
        /// </summary>
        /// <param name="id">El ID del álbum a eliminar.</param>
        /// <returns>Sin contenido si la eliminación es exitosa.</returns>
        // DELETE: api/Albums/5
        [HttpDelete("{id}")]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        public async Task<IActionResult> DeleteAlbum(int id)
        {
            try
            {
                var album = await _context.Albums.Include(a => a.Artist).Include(t => t.Tracks).FirstOrDefaultAsync(a => a.AlbumId == id);
                if (album == null)
                {
                    ProblemDetails problemDetails = CreateProblemDetails(StatusCodes.Status404NotFound, "No se ha encontrado", $"No se ha encontrado un álbum con el id especificado: {id}.");
                    return NotFound(problemDetails);
                }

                _context.Albums.Remove(album);
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
        /// Verifica si un álbum con el ID especificado existe en la base de datos.
        /// </summary>
        /// <param name="id">El ID del álbum a verificar.</param>
        /// <returns>True si el álbum existe; de lo contrario, false.</returns>
        private bool AlbumExists(int id)
        {
            return (_context.Albums?.Any(e => e.AlbumId == id)).GetValueOrDefault();
        }
    }
}
