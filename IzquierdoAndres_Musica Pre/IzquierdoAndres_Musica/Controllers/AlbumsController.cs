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
    public class AlbumsController : Controller
    {
        private readonly ChinookContext _context;

        public AlbumsController(ChinookContext context)
        {
            _context = context;
        }

        // GET: Albums
        public async Task<IActionResult> Index()
        {
            var chinookContext = _context.Albums.Include(a => a.Artist);
            return View(await chinookContext.ToListAsync());
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
            ViewData["a"] = new SelectList(_context.Artists, "ArtistId", "Name");
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
            ViewData["Artist"] = new SelectList(_context.Artists, "ArtistId", "Name");
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
            ViewData["Artist"] = new SelectList(_context.Artists, "Name");
            return View(album);
        }

        // POST: Albums/Edit/5

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
                return RedirectToAction(nameof(Index));
            }
            ViewData["ArtistId"] = new SelectList(_context.Artists, "ArtistId", "ArtistId", album.ArtistId);
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
                return Problem("Entity set 'ChinookContext.Albums'  is null.");
            }
            var album = await _context.Albums.FindAsync(id);
            var tracksToDelete = await _context.Tracks.Where(t => t.AlbumId == id).ToListAsync();

            //var album = await _context.Albums.Include(a => a.Tracks).ThenInclude(t => t.PlaylistTracks).FirstOrDefaultAsync(a => a.AlbumId == id);

            if (album != null)
            {
                foreach (var track in tracksToDelete) { 
                    var tracksInPlaylist = await _context.PlaylistTracks.Where(t => t.TrackId == track.TrackId).ToListAsync();
                    
                    tracksInPlaylist.ForEach(playlist => _context.PlaylistTracks.Remove(playlist));

                    _context.Tracks.Remove(track);
                }

                _context.Albums.Remove(album);
                await _context.SaveChangesAsync();
            }

            return RedirectToAction(nameof(Index));
        }

        private bool AlbumExists(int id)
        {
          return (_context.Albums?.Any(e => e.AlbumId == id)).GetValueOrDefault();
        }
    }
}
