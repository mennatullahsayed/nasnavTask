package demo.nasnav.task.security.services;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import demo.nasnav.task.models.Uploads;
import demo.nasnav.task.models.Status;
import demo.nasnav.task.models.User;
import demo.nasnav.task.repository.UserRepository;

@Service
public class UserUploadsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public UploadsService uploadsService;

	public void UploadsPicture(MultipartFile file, Uploads picture) throws IOException {
		Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String loginUsername = ((UserDetails) principalObject).getUsername();
		Optional<User> user = userRepository.findByUsername(loginUsername);

		if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("jpg")
				|| FilenameUtils.getExtension(file.getOriginalFilename()).equals("png")
				|| FilenameUtils.getExtension(file.getOriginalFilename()).equals("gif")) {
			throw new FileUploadException("file type not allowed");
		}
		picture.setOriginalFilename(file.getOriginalFilename());
		picture.setAttachment(file.getBytes());
		picture.setUser(user.get());
		picture.setStatus(Status.unprocessed);
		uploadsService.save(picture);

	}

}
