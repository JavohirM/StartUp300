package uz.davrbankautoelon.davrbank.controller.image;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.davrbankautoelon.davrbank.dto.TestDto;
import uz.davrbankautoelon.davrbank.dto.auth.UploadFileResponse;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.auth.image.ImageSaveService;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Base64;

@RestController
public class ImageController {

    private final ImageSaveService service;

    public ImageController(ImageSaveService service) {
        this.service = service;
    }


    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(String file) {

        MultipartFile multipartFile = service.uploadFile(file);
        String fileName = service.storeFile(multipartFile);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/v1/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri,
                multipartFile.getContentType(), multipartFile.getSize());
    }

    public UploadFileResponse uploadPdf(String file) {

        MultipartFile multipartFile = service.uploadPdf(file);
        String fileName = service.storePdf(multipartFile);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/v1/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri,
                multipartFile.getContentType(), multipartFile.getSize());
    }


    @GetMapping("api/v1/downloadFile/{fileName:.+}")
    public String downloadFile(@PathVariable String fileName) throws Exception {
        // Load file as Resource
        Resource resource = service.loadFileAsResource(fileName);

        // read image from file
        FileInputStream stream = new FileInputStream(resource.getFile().getAbsolutePath());

        // get byte array from image stream
        int bufLength = 2048;
        byte[] buffer = new byte[2048];
        byte[] data;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int readLength;
        while ((readLength = stream.read(buffer, 0, bufLength)) != -1) {
            out.write(buffer, 0, readLength);
        }
        data = out.toByteArray();
        String imageString = Base64.getEncoder().withoutPadding().encodeToString(data);
        out.close();
        stream.close();

        return imageString;
    }
}
