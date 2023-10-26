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

        public AlbumsController(LocalDBChinookContext context)
        {
            _context = context;
        }

        // GET: Albums
        public async Task<IActionResult> Index()
        {
            //var localDBChinookContext = _context.Albums.Include(a => a.Artist).OrderByDescending(i => i.AlbumId).Take(15);
            var localDBChinookContext = _context.Albums.Include(a => a.Artist).OrderByDescending(i => i.AlbumId);

            return View(await localDBChinookContext.ToListAsync());
        }

        public async Task<IActionResult> Show(int id)
        {
            var localDBChinookContext = _context.Albums.Include(a => a.Artist).Where(a => a.ArtistId == id).OrderByDescending(i => i.AlbumId);
            return View(await localDBChinookContext.ToListAsync());
        }


        // GET: Albums/Details/5
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
        public IActionResult Create()
        {
            ViewData["Artist"] = new SelectList(_context.Artists, "ArtistId", "Name");
            return View();
        }

        // POST: Albums/Create

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
                var anyTracks = album.Tracks.Count();

                if (anyTracks != 0)
                {
                    var tracksToDelete = await _context.Tracks.Where(t => t.AlbumId == id).ToListAsync();

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
            else
            {
                return Problem($"No se encontre un álbum con el id: {id}");
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        public async Task<IActionResult> ShowSongList(int id)
        {
            var artistSongs = _context.Tracks.Where(a => a.AlbumId == id);

            return View(await artistSongs.ToListAsync());
        }

        

        private bool AlbumExists(int id)
        {
          return (_context.Albums?.Any(e => e.AlbumId == id)).GetValueOrDefault();
        }
    }
}
