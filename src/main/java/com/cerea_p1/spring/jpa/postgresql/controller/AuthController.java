package com.cerea_p1.spring.jpa.postgresql.controller;


import java.util.Optional;

/* import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors; */
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;
<<<<<<< HEAD
//import com.cerea_p1.spring.jpa.postgresql.model.Amigo;
=======
import com.cerea_p1.spring.jpa.postgresql.model.Amigo;
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
import com.cerea_p1.spring.jpa.postgresql.payload.request.LoginRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.SignupRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.AddFriendRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.response.JwtResponse;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;
<<<<<<< HEAD
//import com.cerea_p1.spring.jpa.postgresql.repository.AmigoRepository;
=======
import com.cerea_p1.spring.jpa.postgresql.repository.AmigoRepository;
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.security.jwt.JwtUtils;
import com.cerea_p1.spring.jpa.postgresql.security.services.UserDetailsImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
<<<<<<< HEAD
import java.util.logging.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
=======
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UsuarioRepository userRepository;
	@Autowired
<<<<<<< HEAD
//	AmigoRepository friendRepository;
	private static final Logger logger = Logger.getLogger("MyLog");
=======
	AmigoRepository friendRepository;
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
	
//	@Autowired
//	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
<<<<<<< HEAD
		// return ResponseEntity.ok(new JwtResponse(jwt, 
		// 										 userDetails.getUsername(), 
		// 										 userDetails.getEmail(), 
		// 										 userDetails.getPais(),
		// 										 userDetails.getPuntos(),
		// 										 userDetails.getAmigos()));
=======
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 userDetails.getPais(),
<<<<<<< HEAD
												 userDetails.getPuntos()));

=======
												 userDetails.getPuntos(),
												 userDetails.getAmigos()));
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
<<<<<<< HEAD
		logger.info("user=" + signUpRequest.getUsername() + " email=" + signUpRequest.getEmail() + " pais=" + signUpRequest.getPais() + " pass=" + signUpRequest.getPassword());
=======
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		Usuario user = new Usuario(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),signUpRequest.getPais());
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

<<<<<<< HEAD
	// @GetMapping("/addfriend")
	// public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest addfriendRequest) {
	// 	logger.info("user1=" + addfriendRequest.getUsername() + " user2=" + addfriendRequest.getFriendname());
	// 	if ( (!userRepository.existsByUsername(addfriendRequest.getUsername())) ||(!userRepository.existsByUsername(addfriendRequest.getFriendname())) ) {
	// 		return ResponseEntity
	// 				.badRequest()
	// 				.body(new MessageResponse("Error: User or friend is not registered"));
	// 	} else {
	// 	//	Usuario friend = userRepository.findByUsername(addfriendRequest.getFriendname()).get();
	// 		friendRepository.save(new Amigo(addfriendRequest.getUsername(),addfriendRequest.getFriendname()));
	// 		return ResponseEntity.ok(new MessageResponse("Friend added successfully!"));
	// 	}
	// }
=======
	@GetMapping("/addfriend")
	public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest addfriendRequest) {
		if (!userRepository.existsByUsername(addfriendRequest.getUsername())||!userRepository.existsByUsername(addfriendRequest.getFriendname())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: User or friend is not registered"));
		} else {
			Usuario friend = userRepository.findByUsername(addfriendRequest.getFriendname()).get();
			friendRepository.save(new Amigo(addfriendRequest.getUsername(),friend.getUsername()));
		}
		return ResponseEntity.ok(new MessageResponse("Friend added successfully!"));
	}
	
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
}