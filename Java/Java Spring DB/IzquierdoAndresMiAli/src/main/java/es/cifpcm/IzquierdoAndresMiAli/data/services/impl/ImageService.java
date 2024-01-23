package es.cifpcm.IzquierdoAndresMiAli.data.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService implements es.cifpcm.IzquierdoAndresMiAli.data.services.StorageService{
    private static final String RUTA_UPLOADS_TARGET = "static/uploadsIzquierdo";

    @Override
    public void saveImage(MultipartFile file)  {
        try{
            File uploadDirTarget = ResourceUtils.getFile("classpath:" + RUTA_UPLOADS_TARGET);
            Path pathTarget = Paths.get(uploadDirTarget.getAbsolutePath(), file.getOriginalFilename());
            Files.write(pathTarget, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public void deleteIamge(String filename) {

    }
}
