package uz.davrbankautoelon.davrbank.service.auth.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.davrbankautoelon.davrbank.utils.FileStorageProperties;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class ImageSaveService {
    private final Path fileStorageLocation;

    public ImageSaveService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();


        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException();
        }

    }

    public String storeFile(MultipartFile file) {
        log.warn(file.getContentType());
        UUID uuid = UUID.randomUUID();
        String fileName = StringUtils.cleanPath(uuid + ".jpg");
        try {


            if (fileName.contains("..")) {
                throw new RuntimeException();
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
    public String storePdf(MultipartFile file) {
        log.warn(file.getContentType());
        UUID uuid = UUID.randomUUID();
        String fileName = StringUtils.cleanPath(uuid + ".pdf");
        try {


            if (fileName.contains("..")) {
                throw new RuntimeException();
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException();
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException();
        }
    }

    public MultipartFile uploadFile(String base64) {
        final String[] base64Array = base64.split(",");
        String dataUir, data;
        if (base64Array.length > 1) {
            dataUir = base64Array[0];
            data = base64Array[1];
        } else {
            //Build according to the specific file you represent
            dataUir = "data:image/jpg;base64";
            data = base64Array[0];
        }
        MultipartFile multipartFile = new Base64ToMultipartFile(data, dataUir);
        return multipartFile;
    }

    public MultipartFile uploadPdf(String base64) {
        final String[] base64Array = base64.split(",");
        String dataUir, data;
        if (base64Array.length > 1) {
            dataUir = base64Array[0];
            data = base64Array[1];
        } else {
            //Build according to the specific file you represent
            dataUir = "data:application/pdf;base64";
            data = base64Array[0];
        }
        MultipartFile multipartFile = new Base64ToMultipartFile(data, dataUir);
        return multipartFile;
    }
}
