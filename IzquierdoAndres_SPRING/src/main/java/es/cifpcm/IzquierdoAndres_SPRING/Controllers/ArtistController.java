package es.cifpcm.IzquierdoAndres_SPRING.Controllers;

import es.cifpcm.IzquierdoAndres_SPRING.Data.Services.ArtistService;
import es.cifpcm.IzquierdoAndres_SPRING.Models.Album;
import es.cifpcm.IzquierdoAndres_SPRING.Models.Artist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Controller
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public String listaArtistas(Model model) {
        List<Artist> artists = artistService.getSortedArtists().subList(0,15);
        List<Artist> allArtists = artistService.getAllArtistsNameAsc();
        model.addAttribute("allArtists", allArtists);
        model.addAttribute("artists", artists);
        return "artist_templates/list";
    }

    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        model.addAttribute("artist", new Artist());
        return "artist_templates/create";
    }

    @PostMapping("/crear")
    public String createArtist(@Valid @ModelAttribute("artist") Artist artist, Model model,  BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/crear";
        }
        artistService.save(artist);
        model.addAttribute("album", artist);
        return "redirect:/artist";
    }

    @PostMapping("/{id}/delete")
    public String deleteArtist(@Valid @NotNull @PathVariable("id") Integer id) {
        artistService.delete(id);
        return "redirect:/artist";
    }

    @GetMapping("/{id}/edit")
    public String updateArtistForm(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Artist artistById = artistService.getById(id);
        model.addAttribute("artist", artistById);
        return "artist_templates/update";
    }

    @PostMapping("/{id}/edit")
    public String updateArtist(@Valid @NotNull @PathVariable("id") Integer id,
                              @Valid @ModelAttribute("artist") Artist artist) {
        artistService.update(id, artist);

        return "redirect:/artist/" + id;
    }

    @GetMapping("/{id}")
    public String getById(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Artist artistById = artistService.getById(id);
        model.addAttribute("artist", artistById);
        return "artist_templates/details";
    }

    @GetMapping("/discos")
    public String getDiscos(@RequestParam(value="artistId", required = false) Integer artistId,
                            Model model) {
        Artist artistById = artistService.getById(artistId);
        model.addAttribute("artist", artistById);
        return "artist_templates/discos";
    }
}
