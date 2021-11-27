package demo.nasnav.task.security.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import demo.nasnav.task.models.Uploads;
import demo.nasnav.task.models.Status;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {

	 @Autowired
	    public UploadsService uploadsService;
	
	public List<byte[]> getUnprocessedPictures(){
		log.info("get accepter picture");
    List<Uploads> picStatus = uploadsService.getPicturesByStatus(Status.unprocessed);
    List<byte[]> attachments = new ArrayList<>();


    for(int i=0 ; i<picStatus.size() ; i++){
        attachments.add(picStatus.get(i).getAttachment());
    }
    return attachments;
    
	}

	 public ResponseEntity<HashMap<String , Object>> getPictureById(int pictureId) {
		try {
			
		
		  log.info("get uploaded pic  by ID {}", pictureId);
	        var picture = uploadsService.findById(pictureId);
	        Uploads pic = picture.get();
	        if (picture.isPresent()) {
	            BufferedImage image = null;
	            try {
	                image = ImageIO.read(new ByteArrayInputStream(pic.getAttachment()));
	            } catch (IOException e) {
	            	log.error("IOException: {}", e.getMessage());
	                e.printStackTrace();
	            }
	            HashMap<String, Object> map = new HashMap<>();
	            map.put("Width" , image.getWidth());
	            map.put("Height" , image.getHeight());
	            map.put("UploadedPicture" , pic.getAttachment());
	            map.put("Description", pic.getDescription());
	            map.put("category" , pic.getCategory());
	           
	            return ResponseEntity.ok(map);
	        } else {
	            log.info("does not exist");
	            return ResponseEntity.notFound().build();
	        }
		} catch (Exception e) {
			 return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<Object> updateStatus(int pictureId, String status) {
		try {
			Optional<Uploads> pic = uploadsService.findById(pictureId);
			if(pic.isPresent())
			return uploadsService.updateStatus(pictureId , status);
			else
				return ResponseEntity.notFound().build(); 
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
       

		
	}
}
