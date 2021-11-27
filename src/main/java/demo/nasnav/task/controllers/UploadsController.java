package demo.nasnav.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.nasnav.task.security.services.UploadsService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/all")

public class UploadsController {

	@Autowired
	public UploadsService uploadsService;

 

	@GetMapping("/pictures/accepted")
	public List<String> getAcceptedPictures(){
		
	return	uploadsService.getAcceptedPictures();
		
	}

	@GetMapping("/picture/{pictureId}")
	public ResponseEntity<HashMap<String , Object>> getPictureById(@PathVariable int pictureId) {
	return	uploadsService.getPictureById(pictureId);
	}
}
