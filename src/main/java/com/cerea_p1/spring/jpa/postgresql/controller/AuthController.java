package com.cerea_p1.spring.jpa.postgresql.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
import com.cerea_p1.spring.jpa.postgresql.payload.request.Profile.OlvidoContraseña;
import com.cerea_p1.spring.jpa.postgresql.payload.request.Profile.ReestablecerContraseña;
import com.cerea_p1.spring.jpa.postgresql.payload.response.JwtResponse;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;

import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.security.jwt.JwtUtils;
import com.cerea_p1.spring.jpa.postgresql.security.services.UserDetailsImpl;
import com.cerea_p1.spring.jpa.postgresql.security.services.UserDetailsServiceImpl;
import com.cerea_p1.spring.jpa.postgresql.utils.Sender;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.*;
@CrossOrigin(allowCredentials = "true", origins = "http://localhost:4200/")
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
    private JavaMailSender mailSender;
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
	public ResponseEntity<?> processForgotPassword(@Valid @RequestBody OlvidoContraseña olv) throws MessagingException, UnsupportedEncodingException {
		String email = olv.getEmail();
		String token = RandomString.make(30);
		if(userRepository.existsByEmail(olv.getEmail())){
			userService.updateResetPasswordToken(token, email);
			// String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, token);

			return ResponseEntity.ok(new MessageResponse("Se ha enviado el correo correctamente."));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede enviar la petición de amistad"));
		}		
	}

	@PostMapping("/reset_password")
	public ResponseEntity<?> processResetPassword(@Valid @RequestBody ReestablecerContraseña olv) {
		String token = olv.getToken();//.getParameter("token");
		String password = olv.getPassword();//request.getParameter("password");
		
		Usuario user = userService.getByResetPasswordToken(token);
	//	model.addAttribute("title", "Reset your password");
		
		if (user == null) {
			
			return ResponseEntity.badRequest().body("Ha fallado");
		} else {           
			userService.updatePassword(user, password);
			
			return ResponseEntity.ok(new MessageResponse("La contraseña se ha restablecido correctamente"));
		}
	}


	private void sendEmail(String recipientEmail, String token)
        throws MessagingException, UnsupportedEncodingException {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
		message.setFrom("noreply@baeldung.com");
		message.setTo(recipientEmail); 
		message.setSubject("Olvidó su contraseña"); 
		message.setText("Para restablecer su contraseña, indique el siguiente token: " + token);
		((org.springframework.mail.javamail.JavaMailSender) mailSender).send(message);
			
		
	}

	// @Bean
	// public JavaMailSenderImpl getJavaMailSender() {
	// 	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	// 	mailSender.setHost("smtp.gmail.com");
	// 	mailSender.setPort(587);
		
	// 	mailSender.setUsername("onep1game@gmail.com");
	// 	mailSender.setPassword("*onep1-Game");
		
	// 	Properties props = mailSender.getJavaMailProperties();
	// 	props.put("mail.transport.protocol", "smtp");
	// 	props.put("mail.smtp.auth", "true");
	// 	props.put("mail.smtp.starttls.enable", "true");
	// 	props.put("mail.debug", "true");
		
	// 	return mailSender;
	// }
}