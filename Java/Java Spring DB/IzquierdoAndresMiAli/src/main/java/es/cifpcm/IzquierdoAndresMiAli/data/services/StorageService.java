package es.cifpcm.IzquierdoAndresMiAli.data.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void saveImage(MultipartFile file);
}
