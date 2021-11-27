package demo.nasnav.task.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo.nasnav.task.models.RoleEnum;
import demo.nasnav.task.models.Role;
import demo.nasnav.task.models.User;
import demo.nasnav.task.payload.request.LoginRequest;
import demo.nasnav.task.payload.request.SignUpRequest;
import demo.nasnav.task.payload.response.JwtResponse;
import demo.nasnav.task.payload.response.MessageResponse;
import demo.nasnav.task.repository.RoleRepository;
import demo.nasnav.task.repository.UserRepository;
import demo.nasnav.task.security.jwt.JwtUtils;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	public ResponseEntity<?> login(@Valid LoginRequest loginRequest) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generateJwtToken(auth);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	public ResponseEntity<?> register(@Valid SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Username is already in exist"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Email is already in exist"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String>Roles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (Roles == null) {
			Role userRole = roleRepository.findByName(RoleEnum.USER)
					.orElseThrow(() -> new RuntimeException("Role is not found."));
			roles.add(userRole);
		} else {
				System.out.println(roleRepository.findByName(RoleEnum.ADMIN));		
			for (String hisRole : Roles) {
				if (hisRole.equals("admin") ) {
					Role adminRole = roleRepository.findByName(RoleEnum.ADMIN)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(adminRole);
				}
				else 
				{
					Role userRole = roleRepository.findByName(RoleEnum.USER)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(userRole);
				}
				
			}
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Registered successfully!"));
	}
	
	
}
