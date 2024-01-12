package es.cifpcm.izquierdoandresgaleria.controllers;

import es.cifpcm.izquierdoandresgaleria.data.FileImageSystemService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GaleriaImagenesServlet {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();


    private final FileImageSystemService imageService;

    public GaleriaImagenesServlet(FileImageSystemService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:imageManager";
    }

    @PostMapping("imageManager")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            imageService.saveImage(file);
            redirectAttributes.addFlashAttribute("message", "¡Subiste con éxito " + file.getOriginalFilename() + "!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:imageManager";
    }

    @GetMapping("imageManager")
    public String listUploadedFiles(Model model) throws IOException {
        List<String> fileNames = imageService.loadAllImages().stream()
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
        model.addAttribute("files", fileNames);
        return "imageList";
    }
}
