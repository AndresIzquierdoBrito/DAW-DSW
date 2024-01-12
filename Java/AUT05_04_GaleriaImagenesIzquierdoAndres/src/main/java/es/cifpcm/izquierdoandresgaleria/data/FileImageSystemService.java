package es.cifpcm.izquierdoandresgaleria.data;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileImageSystemService implements ImageService{

    private final Path imageGalleryLocation = Paths.get("src/main/resources/images");

    @Override
    public void saveImage(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), this.imageGalleryLocation.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public List<Path> loadAllImages() throws IOException {
        try (Stream<Path> stream = Files.walk(this.imageGalleryLocation, 1)) {
            return stream.filter(path -> !path.equals(this.imageGalleryLocation))
                    .collect(Collectors.toList());
        }
    }
}
