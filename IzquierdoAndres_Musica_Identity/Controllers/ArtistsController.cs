using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using IzquierdoAndres_Musica.Data;
using IzquierdoAndres_Musica.Models;

namespace IzquierdoAndres_Musica.Controllers
{
    public class ArtistsController : Controller
    {
        private readonly LocalDBChinookContext _context;

        // Este es el constructor de la clase ArtistsController. Inyecta una instancia de LocalDBChinookContext, 
        // que es la clase de contexto para interactuar con la base de datos a través de Entity Framework Core. 
        // Este contexto de base de datos se asigna a la variable privada _context para su uso en otros métodos 
        // dentro de la clase.

        public ArtistsController(LocalDBChinookContext context)
        {
            _context = context;
        }

        // GET: Artists

        // Devuelve una vista con la lista de los primeros 15 artistas ordenados por nombre. Si el conjunto de artistas 
        // en el contexto de la base de datos es nulo, devuelve un problema HTTP 500 
        public async Task<IActionResult> Index()
        {
            return _context.Artists != null ? 
                        View(await _context.Artists.OrderBy(a => a.Name).Take(15).ToListAsync()) :
                        Problem("Entity set 'LocalDBChinookContext.Artists'  is null.");
        }

        // GET: Artists/Details/5

        // Devuelve una vista con los detalles de un artista específico. Si el id proporcionado es nulo, o si no se 
        // encuentra ningún artista con ese id, devuelve un error HTTP 404.
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Artists == null)
            {
                return NotFound();
            }

            var artist = await _context.Artists
                .FirstOrDefaultAsync(m => m.ArtistId == id);
            if (artist == null)
            {
                return NotFound();
            }

            return View(artist);
        }

        // GET: Artists/Create

        // Devuelve una vista para crear un nuevo artista.
        public IActionResult Create()
        {
            return View();
        }

        // POST: Artists/Create
        
        // Recoge en el argumento un modelo de artista introducido por la vista en un formulario.
        // Si el modelo es válido, agrega el nuevo artista al contexto de la base de datos y guarda los cambios. 
        // Si el modelo no es válido, devuelve la vista de creación con el mismo artista.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Name")] Artist artist)
        {
            artist.ArtistId = _context.Artists.Max(a => a.ArtistId) + 1;
            if (ModelState.IsValid)
            {
                _context.Add(artist);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(artist);
        }

        // GET: Artists/Edit/5

        // Devuelve una vista para editar un artista existente. Si el id proporcionado es nulo, o si no se 
        // encuentra ningún artista con ese id, devuelve un error HTTP 404.
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Artists == null)
            {
                return NotFound();
            }

            var artist = await _context.Artists.FindAsync(id);
            if (artist == null)
            {
                return NotFound();
            }
            return View(artist);
        }

        // POST: Artists/Edit/5

        // Recoge en el argumento un modelo introducido por la vista en un formulario.
        // Si el id proporcionado no coincide con el ArtistId del artista, devuelve un error HTTP 404. 
        // Si el modelo es válido, actualiza el artista en el contexto de la base de datos y guarda los cambios.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("ArtistId,Name")] Artist artist)
        {
            if (id != artist.ArtistId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(artist);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ArtistExists(artist.ArtistId))
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
            return View(artist);
        }

        // GET: Artists/Delete/5

        // Devuelve una vista para eliminar un artista existente. Si el id proporcionado es nulo, 
        // o si no se encuentra ningún artista con ese id, devuelve un error HTTP 404.
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Artists == null)
            {
                return NotFound();
            }

            var artist = await _context.Artists.FirstOrDefaultAsync(m => m.ArtistId == id);

            if (artist == null)
            {
                return NotFound();
            }

            return View(artist);
        }

        // POST: Artists/Delete/5

        // Elimina un artista existente. Si el conjunto de artistas en el contexto de la base de datos es nulo, devuelve 
        // un problema HTTP 500. Si el artista existe, elimina todas las pistas asociadas a los álbumes del artista, 
        // luego elimina todos los álbumes del artista, y finalmente elimina al artista.
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Artists == null)
            {
                return Problem("Entity set 'LocalDBChinookContext.Artists'  is null.");
            }

            var artist = await _context.Artists.Include(a => a.Albums).ThenInclude(t => t.Tracks).FirstOrDefaultAsync(a => a.ArtistId == id);

            if (artist != null)
            {
                var albums = artist.Albums;

                //var albums = await _context.Albums.Where(a => a.ArtistId == artist.ArtistId).ToListAsync();

                if (albums != null)
                {

                    foreach (var album in albums)
                    {
                        var anyTracks = album.Tracks.Count();

                        if (anyTracks != 0)
                        {
                            var tracksToDelete = await _context.Tracks.Where(t => t.AlbumId == album.AlbumId).ToListAsync();

                            foreach (var track in tracksToDelete)
                            {
                                var tracksInPlaylist = await _context.PlaylistTracks.Where(t => t.TrackId == track.TrackId).ToListAsync();
                                var invoiceLine = await _context.InvoiceLines.Where(t => t.TrackId == track.TrackId).ToListAsync();

                                tracksInPlaylist.ForEach(playlist => _context.PlaylistTracks.Remove(playlist));

                                invoiceLine.ForEach(invoiceLine => _context.InvoiceLines.Remove(invoiceLine));

                                _context.Tracks.Remove(track);
                            }
                        }
                        _context.Albums.Remove(album);
                    }
                }
                _context.Artists.Remove(artist);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        // GET: Artists/ShowAlbumList/5

        // Devuelve una vista con la lista de álbumes de un artista específico, ordenados en orden descendente por AlbumId.
        public async Task<IActionResult> ShowAlbumList(int id)
        {
            var localDBChinookContext = _context.Albums.Include(a => a.Artist)
                                                    .Where(a => a.ArtistId == id)
                                                    .OrderByDescending(i => i.AlbumId);
            return View(await localDBChinookContext.ToListAsync());
        }


        // Verifica si existe un artista con el id proporcionado en el contexto de la base de datos. 
        // Retorna true si el artista existe y false si no
        private bool ArtistExists(int id)
        {
          return (_context.Artists?.Any(e => e.ArtistId == id)).GetValueOrDefault();
        }
    }
}
