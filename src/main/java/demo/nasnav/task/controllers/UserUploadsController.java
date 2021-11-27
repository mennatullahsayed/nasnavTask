package demo.nasnav.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import demo.nasnav.task.models.Uploads;
import demo.nasnav.task.security.services.UserUploadsService;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserUploadsController {

    @Autowired
    private UserUploadsService userUploadsService;
    
    @PostMapping("/picture")
    public void UploadsPicture(@RequestPart("file") MultipartFile file, @RequestPart Uploads uploadsPicture) throws IOException {

    	userUploadsService.UploadsPicture(file,uploadsPicture);
    	
    
    }
}
