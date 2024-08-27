package com.ecommerce.system.service.util;

import com.ecommerce.system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class ProductUtil {

    @Value("${file.upload.base-path}")
    private String basePath;

    public String storeImage(MultipartFile file, String pathType) throws IOException {
        Path fileStorageLocation = Paths.get(basePath + pathType).toAbsolutePath().normalize();
        Files.createDirectories(fileStorageLocation);
        String fileName = StringUtils.cleanPath(UUID.randomUUID() + "-" + file.getOriginalFilename());
        if (fileName.contains(".."))
            throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
        Path destinationLocation = fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), destinationLocation, StandardCopyOption.REPLACE_EXISTING);
        return destinationLocation.toString();
    }

}
