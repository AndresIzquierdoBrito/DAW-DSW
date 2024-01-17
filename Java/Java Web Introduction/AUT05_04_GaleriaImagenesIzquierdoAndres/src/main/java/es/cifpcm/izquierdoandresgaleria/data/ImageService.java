package es.cifpcm.izquierdoandresgaleria.data;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ImageService {
    void saveImage(MultipartFile file) throws IOException;
    List<Path> loadAllImages() throws IOException;
}
