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

import com.cerea_p1.spring.jpa.postgresql.model.Amigo;

import com.cerea_p1.spring.jpa.postgresql.payload.request.LoginRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.SignupRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.friends.AddFriendRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.response.JwtResponse;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;

import com.cerea_p1.spring.jpa.postgresql.repository.AmigoRepository;

import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.security.jwt.JwtUtils;
import com.cerea_p1.spring.jpa.postgresql.security.services.UserDetailsImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
	UsuarioRepository userRepository;
	@Autowired
	AmigoRepository friendRepository;
	private static final Logger logger = Logger.getLogger("MyLog");

	@Autowired
	JwtUtils jwtUtils;

    @GetMapping("/send/friend-request")
	public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest addfriendRequest) {
		logger.info("user1=" + addfriendRequest.getUsername() + " user2=" + addfriendRequest.getFriendname());
		if ( (!userRepository.existsByUsername(addfriendRequest.getUsername())) ||(!userRepository.existsByUsername(addfriendRequest.getFriendname())) ) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: User or friend is not registered"));
		} else {
		//	Usuario friend = userRepository.findByUsername(addfriendRequest.getFriendname()).get();
			//friendRepository.save(new Amigo(addfriendRequest.getUsername(),addfriendRequest.getFriendname()));

            // NO QUEREMOS AÑADIR A AMIGOS DIRECTAMENTE, QUEREMOS ENVIAR UNA PETICIÓN DE AMISTAD QUE TENDRÁ QUE SER ACEPTADA!

			return ResponseEntity.ok(new MessageResponse("Friend added successfully!"));
		}
	}
}
