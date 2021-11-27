package demo.nasnav.task.controllers;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import demo.nasnav.task.payload.request.LoginRequest;
import demo.nasnav.task.payload.request.SignUpRequest;
import demo.nasnav.task.security.services.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired 
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest Request) {
		return authService.login(Request);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest Request) {
		return authService.register(Request);
	}
}
