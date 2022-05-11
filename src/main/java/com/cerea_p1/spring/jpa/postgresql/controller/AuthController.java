package com.cerea_p1.spring.jpa.postgresql.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.MessagingException;
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

import net.bytebuddy.utility.RandomString;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

import com.cerea_p1.spring.jpa.postgresql.payload.request.LoginRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.SignupRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.Profile.OlvidoContrasena;
import com.cerea_p1.spring.jpa.postgresql.payload.request.Profile.ReestablecerContrasena;
import com.cerea_p1.spring.jpa.postgresql.payload.response.JwtResponse;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;

import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.security.jwt.JwtUtils;
import com.cerea_p1.spring.jpa.postgresql.security.services.UserDetailsImpl;
import com.cerea_p1.spring.jpa.postgresql.security.services.UserDetailsServiceImpl;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;
import java.util.logging.*;
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200/", "https://one-fweb.herokuapp.com"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UsuarioRepository userRepository;
	private static final Logger logger = Logger.getLogger("MyLog");
	@Autowired
	UserDetailsServiceImpl userService;

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private JavaMailSender javaMailSender;
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate( 
			
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 userDetails.getPais(),
												 userDetails.getPuntos()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		logger.info("user=" + signUpRequest.getUsername() + " email=" + signUpRequest.getEmail() + " pais=" + signUpRequest.getPais() + " pass=" + signUpRequest.getPassword());

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


	@PostMapping("/forgot_password")
	public ResponseEntity<?> processForgotPassword(@Valid @RequestBody OlvidoContrasena olv) throws MessagingException, UnsupportedEncodingException {
		String email = olv.getEmail();
		String token = RandomString.make(30);
		if(userRepository.existsByEmail(olv.getEmail())){
			userService.updateResetPasswordToken(token, email);
			sendEmail(email, token);

			return ResponseEntity.ok(new MessageResponse("Se ha enviado el correo correctamente."));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede enviar el correo de recuperacion"));
		}		
	}

	@PostMapping("/reset_password")
	public ResponseEntity<?> processResetPassword(@Valid @RequestBody ReestablecerContrasena olv) {
		String token = olv.getToken();//.getParameter("token");
		String password = olv.getPassword();//request.getParameter("password");
		
		Usuario user = userService.getByResetPasswordToken(token);
		
		if (user == null) {
			
			return ResponseEntity.badRequest().body("Ha fallado");
		} else {
			user.setPassword(encoder.encode(password)); 
			user.setResetPasswordToken(null);
			userRepository.save(user);          
			
			return ResponseEntity.ok(new MessageResponse("La contraseña se ha restablecido correctamente"));
		}
	}

	void sendEmail(String to, String token) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);

        msg.setSubject("Recuperar contraseña");
        msg.setText("Para restablecer su contraseña, introduzca el siguiente token: " + token);

        javaMailSender.send(msg);

    }

}