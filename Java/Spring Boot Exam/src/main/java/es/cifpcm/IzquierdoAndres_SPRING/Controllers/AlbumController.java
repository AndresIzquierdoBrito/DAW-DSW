package es.cifpcm.IzquierdoAndres_SPRING.Controllers;

import es.cifpcm.IzquierdoAndres_SPRING.Data.Services.AlbumService;
import es.cifpcm.IzquierdoAndres_SPRING.Data.Services.ArtistService;
import es.cifpcm.IzquierdoAndres_SPRING.Models.Album;
import es.cifpcm.IzquierdoAndres_SPRING.Models.Artist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Controller
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;
    private final ArtistService artistService;

    public AlbumController(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;

    }

    @GetMapping
    public String listaDiscos(Model model) {
        List<Album> albums = albumService.getSortedAlbums().subList(0,15);
        model.addAttribute("albums", albums);
        return "album_templates/list";
    }

    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        List<Artist> artistas = artistService.getAllArtistsNameAsc();
        model.addAttribute("artists", artistas);
        model.addAttribute("album", new Album());
        return "album_templates/create";
    }

    @PostMapping("/crear")
    public String createAlbum(@Valid @ModelAttribute("album") Album album, Model model,  BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/crear";
        }
        albumService.save(album);
        model.addAttribute("album", album);
        return "redirect:/album";
    }

    @PostMapping("/{id}/delete")
    public String deleteAlbum(@Valid @NotNull @PathVariable("id") Integer id) {
        albumService.delete(id);
        return "redirect:/album";
    }

    @GetMapping("/{id}/edit")
    public String updateAlbumForm(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Album albumById = albumService.getById(id);
        List<Artist> artistas = artistService.getAllArtistsNameAsc();
        model.addAttribute("album", albumById);
        model.addAttribute("artists", artistas);

        return "album_templates/update";
    }

    @PostMapping("/{id}/edit")
    public String updateAlbum(@Valid @NotNull @PathVariable("id") Integer id,
                                 @Valid @ModelAttribute("album") Album album) {
        albumService.update(id, album);

        return "redirect:/album/" + id;
    }

    @GetMapping("/{id}")
    public String getById(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Album albumById = albumService.getById(id);
        model.addAttribute("album", albumById);
        return "album_templates/details";
    }
}
