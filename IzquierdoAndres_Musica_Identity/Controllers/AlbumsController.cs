using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using IzquierdoAndres_Musica.Data;
using IzquierdoAndres_Musica.Models;
using Microsoft.IdentityModel.Tokens;

namespace IzquierdoAndres_Musica.Controllers
{
    public class AlbumsController : Controller
    {
        private readonly LocalDBChinookContext _context;

        // Constructor de la clase AlbumsController.

        // Inyecta una instancia de LocalDBChinookContext, que es la clase de  contexto para interactuar con la base de datos
        // a través de Entity Framework Core. Este contexto de base de datos se asigna a la variable privada _context para su 
        // uso en otros métodos dentro de la clase.
        public AlbumsController(LocalDBChinookContext context)
        {
            _context = context;
        }

        // GET: Albums

        // Devuelve una vista con la lista de los 15 álbumes más recientes. Utiliza el contexto de la base de datos para obtener
        // los álbumes, incluir los detalles del artista, ordenarlos en orden descendente por AlbumId y luego toma 
        // los primeros 15 registros.
        public async Task<IActionResult> Index()
        {
            var localDBChinookContext = _context.Albums.Include(a => a.Artist).OrderByDescending(i => i.AlbumId).Take(15);
            return View(await localDBChinookContext.ToListAsync());
        }

        // GET: Albums/Show 

        // Devuelve una vista con la lista de álbumes de un artista específico, ordenados en orden descendente por AlbumId. 
        // Utiliza el contexto de la base de datos para obtener los álbumes donde ArtistId sea igual al id proporcionado.
        public async Task<IActionResult> Show(int id)
        {
            var localDBChinookContext = _context.Albums.Include(a => a.Artist).Where(a => a.ArtistId == id).OrderByDescending(i => i.AlbumId);
            return View(await localDBChinookContext.ToListAsync());
        }

        // GET: Albums/Details/5

        // Devuelve una vista con los detalles de un álbum específico, incluido el artista. Si el id proporcionado es nulo
        // o no existe ningún álbum con ese id, devuelve un error HTTP 404.
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Albums == null)
            {
                return NotFound();
            }

            var album = await _context.Albums
                .Include(a => a.Artist)
                .FirstOrDefaultAsync(m => m.AlbumId == id);
            if (album == null)
            {
                return NotFound();
            }

            return View(album);
        }

        // GET: Albums/Create

        // Devuelve una vista para crear un nuevo album. Asigna una lista de artistas a ViewData["Artist"] para su uso en la vista.
        public IActionResult Create()
        {
            ViewData["Artist"] = new SelectList(_context.Artists, "ArtistId", "Name");
            return View();
        }

        // POST: Albums/Create

        // Recibe los argumentos del album devueltos del formulario. Si el modelo es válido, agrega el album al contexto de la 
        // base de datos y guarda los cambios. Si el modelo no es válido, vuelve a la vista de creación con el mismo album 
        // y la lista de albums
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Title,ArtistId")] Album album)
        {
            album.AlbumId = _context.Albums.Max(a => a.AlbumId) + 1;
            if (ModelState.IsValid)
            {
                _context.Add(album);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["Artist"] = new SelectList(_context.Artists, "ArtistId", "Name", album.ArtistId);
            return View(album);
        }

        // GET: Albums/Edit/5

         // Devuelve una vista para editar un álbum existente. Si el id proporcionado 
        // es nulo o no existe ningún álbum con ese id, devuelve un error HTTP 404.
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Albums == null)
            {
                return NotFound();
            }

            var album = await _context.Albums.FindAsync(id);
            if (album == null)
            {
                return NotFound();
            }
            ViewData["ArtistId"] = new SelectList(_context.Artists, "ArtistId", "Name", album.ArtistId);
            return View(album);
        }

        // POST: Albums/Edit

        // Los argumentos traen un modelo album sacado de un formulario.  Si el id proporcionado no coincide con el AlbumId 
        // del álbum, devuelve un error HTTP 404. Si el modelo es válido, actualiza el álbum en el contexto de la base de 
        // datos y guarda los cambios.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("AlbumId,Title,ArtistId")] Album album)
        {
            if (id != album.AlbumId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(album);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!AlbumExists(album.AlbumId))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Edit));
            }
            ViewData["ArtistId"] = new SelectList(_context.Artists, "ArtistId", "Name", album.ArtistId);
            return View(album);
        }

        // GET: Albums/Delete/5

        // Devuelve una vista para eliminar un álbum existente. Si el id proporcionado es nulo o no existe ningún álbum 
        // con ese id, devuelve un error HTTP 404.
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Albums == null)
            {
                return NotFound();
            }

            var album = await _context.Albums
                .Include(a => a.Artist)
                .Include(t => t.Tracks)
                .FirstOrDefaultAsync(m => m.AlbumId == id);
            if (album == null)
            {
                return NotFound();
            }

            return View(album);
        }

        // POST: Albums/Delete/5

        // Recoge en los argumentos el album a borrar a traves de un formulario, si no existe ningún álbum con el id proporcionado, 
        // devuelve un error HTTP 404. Si el álbum existe, lo elimina el contexto de la base de datos realizando una serie de 
        // borrados en cascada para borrar todas las dependencias de las entidades de la base de datos y guarda los cambios.
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Albums == null)
            {
                return Problem("Entity set 'LocalDBChinookContext.Albums'  is null.");
            }
            var album = await _context.Albums.Include(a => a.Tracks).FirstOrDefaultAsync(a => a.AlbumId == id);

            if (album != null)
            {
               
                _context.Albums.Remove(album);
            }
            else
            {
                return Problem($"No se encontre un álbum con el id: {id}");
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        // GET: Albums/ShowSongList/5

        // Devuelve una vista con la lista de canciones de un álbum específico. Si no existe ningún álbum con el id proporcionado, 
        // devuelve una lista vacía.
        public async Task<IActionResult> ShowSongList(int id)
        {
            var album = await _context.Albums.Include(a => a.Tracks).FirstOrDefaultAsync(a => a.AlbumId == id);

            if (album != null)
            {
                var anyTracks = album.Tracks.Count();

                if (anyTracks == 0)
                {
                    List<Track> tracks = new List<Track>();
                    return View(tracks);
                }
            }
            var artistSongs = _context.Tracks.Where(a => a.AlbumId == id);

            return View(await artistSongs.ToListAsync());
        }


        // Verifica si existe un álbum con el id proporcionado en el contexto de la base de datos.
        // Retorna true si el álbum existe y false si no 
        private bool AlbumExists(int id)
        {
            return (_context.Albums?.Any(e => e.AlbumId == id)).GetValueOrDefault();
        }
    }
}
