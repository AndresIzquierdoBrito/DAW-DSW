package es.cifpcm.izquierdoandresgaleria.data;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Service
public class FileImageSystemService implements ImageService{

    private static final String RUTA_UPLOADS = "src/main/resources/static/uploadsIzquierdo";
    private static final String RUTA_UPLOADS_TARGET = "static/uploadsIzquierdo";

    @Override
    public void saveImage(MultipartFile file) throws IOException {
        File uploadDirStatic = ResourceUtils.getFile(RUTA_UPLOADS);

        Path pathStatic = Paths.get(uploadDirStatic.getAbsolutePath(), file.getOriginalFilename());
        Files.write(pathStatic, file.getBytes());
        File uploadDirTarget = ResourceUtils.getFile("classpath:" + RUTA_UPLOADS_TARGET);

        Path pathTarget = Paths.get(uploadDirTarget.getAbsolutePath(), file.getOriginalFilename());
        Files.write(pathTarget, file.getBytes());
    }

    @Override
    public List<Path> loadAllImages() throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(RUTA_UPLOADS))) {
            return stream.filter(path -> !path.equals((Paths.get(RUTA_UPLOADS))))
                    .collect(Collectors.toList());
        }
    }
}
