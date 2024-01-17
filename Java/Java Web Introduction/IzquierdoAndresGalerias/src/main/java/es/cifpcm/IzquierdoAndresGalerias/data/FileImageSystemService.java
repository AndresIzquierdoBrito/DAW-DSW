package es.cifpcm.IzquierdoAndresGalerias.data;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileImageSystemService implements ImageService{

    private static final String RUTA_UPLOADS_TARGET = "static/uploadsIzquierdo";
    
    @Override
    public void saveImage(MultipartFile file) throws IOException {

        File uploadDirTarget = ResourceUtils.getFile("classpath:" + RUTA_UPLOADS_TARGET);

        Path pathTarget = Paths.get(uploadDirTarget.getAbsolutePath(), file.getOriginalFilename());
        Files.write(pathTarget, file.getBytes());
    }

    @Override
    public List<Path> loadAllImages() throws IOException {
        File uploadDirTarget = ResourceUtils.getFile("classpath:" + RUTA_UPLOADS_TARGET);

        try (Stream<Path> stream = Files.walk(Paths.get(uploadDirTarget.toURI()))) {
            return stream.filter(path -> !path.toUri().equals(uploadDirTarget.toURI()))
                    .collect(Collectors.toList());
        }
    }
}
