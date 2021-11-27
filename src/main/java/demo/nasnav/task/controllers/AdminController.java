package demo.nasnav.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import demo.nasnav.task.security.services.AdminService;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    
    @Autowired
    private AdminService adminService;
    

    @GetMapping("/pictures/unprocessed")
    public List<byte[]> getUnprocessedPictures() {
    	
    return	adminService.getUnprocessedPictures();
      
    }
 
    @GetMapping("/picture/{pictureId}")
    public ResponseEntity<HashMap<String , Object>> getPictureById(@PathVariable int pictureId) {

    return	adminService.getPictureById(pictureId);
      
    }

    @PatchMapping("/picture/{pictureId}")
    public ResponseEntity<Object> updateStatus(@PathVariable int pictureId , @RequestBody String status) {
        
    	return adminService.updateStatus(pictureId ,status);
    }
}
