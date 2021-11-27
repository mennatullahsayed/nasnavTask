package demo.nasnav.task.security.services;

import com.google.gson.Gson;

import demo.nasnav.task.models.Uploads;
import demo.nasnav.task.models.Status;
import demo.nasnav.task.repository.PictureRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

@Service
@Slf4j
public class UploadsService {
	@Autowired
	ServletContext context;

    @Autowired
    private PictureRepository pictureRepository;

    public List<Uploads> findAll() {
        return pictureRepository.findAll();
    }

    public Optional<Uploads> findById(int Id) {
        return pictureRepository.findById(Id);
    }

    public void deleteById(int id) {
    	pictureRepository.deleteById(id);
    }

    public List<Uploads> getPicturesByStatus(Status status) {
    	return pictureRepository.findAllByStatus(status);
    }

    public void save(Uploads picture) {
        pictureRepository.save(picture);
    }

    public  ResponseEntity<Object> updateStatus(Integer id, String status) {
        Optional<Uploads> pic = pictureRepository.findById(id);
        Gson gson = new Gson();
        LinkedHashMap<String, String> statusObj = gson.fromJson(status , LinkedHashMap.class);
        if(pic.isPresent()){
            Uploads upload = pic.get();
            if(statusObj.get("status").equals("accepted")){
            try {	
            	 byte[] file = upload.getAttachment();
				    Path path =  java.nio.file.Paths.get(context.getRealPath("uploads") +upload.getOriginalFilename());
				    Files.write(path, file);
				    upload.setUrls(path.toString());
             } catch (IOException e) {
            	 return ResponseEntity.notFound().build();
            }
            	
            	
                upload.setStatus(Status.accepted);
                pictureRepository.save(upload);
                return ResponseEntity.ok().build();
            }
            else if(statusObj.get("status").equals("rejected")){
                upload.setStatus(Status.rejected);
                pictureRepository.save(upload);
                return ResponseEntity.ok().build();
            }
        }
		return ResponseEntity.notFound().build();
    }

	public List<String> getAcceptedPictures() {
		log.info("het all accepted picture");
		List<Uploads> picStatus = getPicturesByStatus(Status.accepted);
		List<String> urls = new ArrayList<>();
		for(int i=0 ; i< picStatus.size() ; i++){
			String url = picStatus.get(i).getUrls();
			urls.add(url);
		}
		return urls;
	}

	public ResponseEntity<HashMap<String , Object>> getPictureById(int pictureId) {
		try {
			
		
		log.info("get picture by ID {}", pictureId);
		Optional<Uploads> pic = findById(pictureId);
		Uploads picture = pic.get();
		if (pic.isPresent() && picture.getStatus().equals(Status.accepted)  ) {
			 HashMap<String, Object> map = new HashMap<>();
	        
	            map.put("UploadedPicture" , picture.getAttachment());
	            map.put("Description", picture.getDescription());
	            map.put("category" , picture.getCategory());
	            
	            return ResponseEntity.ok(map);
			
			
		}else {
			log.info("the picture does not exist");
			return ResponseEntity.notFound().build();
		}
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}
}
