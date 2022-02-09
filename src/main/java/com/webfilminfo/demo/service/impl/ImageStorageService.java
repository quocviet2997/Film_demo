package com.webfilminfo.demo.service.impl;

import com.webfilminfo.demo.service.IImageStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageStorageService implements IImageStorageService {
    @Override
    public String storeFile(MultipartFile file) {
        try {
            // Check path to save images
            Path path = Paths.get("src/main/webapp/resources/images").toAbsolutePath();
            checkExistPath(path);

            // check if file is null or not
            if (file.isEmpty())
                throw new RuntimeException("Can't upload empty file");

            // check if extendsion is for image
            if (!isImageFile(file))
                throw new RuntimeException("Can't upload file different from images");

            // File must be rename
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
            Path destPath = path.resolve(Paths.get(newFileName));
            System.out.println(destPath.toString());

            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to store file.", exception);
        }
    }

    public void checkExistPath(Path path){
        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isImageFile(MultipartFile file){
        // let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        // check if file extension if it matches
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

}
