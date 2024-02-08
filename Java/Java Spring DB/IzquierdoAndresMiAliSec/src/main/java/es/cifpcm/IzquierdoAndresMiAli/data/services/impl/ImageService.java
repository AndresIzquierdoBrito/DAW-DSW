package es.cifpcm.IzquierdoAndresMiAli.data.services.impl;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService implements es.cifpcm.IzquierdoAndresMiAli.data.services.StorageService{
    private static final String RUTA_UPLOADS_TARGET = "static/uploads";

    @Override
    public void saveImage(MultipartFile file) {
        try {

            ClassPathResource classPathResource = new ClassPathResource(RUTA_UPLOADS_TARGET);
            Path uploadDirTarget = classPathResource.getFile().toPath();

            if(!Files.exists(uploadDirTarget)){
                Files.createDirectories(uploadDirTarget);
            }

            Path pathTarget = Paths.get(uploadDirTarget.toString(), file.getOriginalFilename());
            Files.copy(file.getInputStream(), pathTarget);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
