package com.cerea_p1.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.cerea_p1.spring.jpa.postgresql.payload.request.friends.AddFriendRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.friends.GetFriendRequest;

import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;

import com.cerea_p1.spring.jpa.postgresql.repository.AmigoRepository;
import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.repository.InvitacionAmistadRepository;

import com.cerea_p1.spring.jpa.postgresql.model.friends.InvitacionAmistad;

import com.cerea_p1.spring.jpa.postgresql.security.jwt.JwtUtils;

import com.cerea_p1.spring.jpa.postgresql.utils.Sender;


import java.util.logging.*;


@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
	UsuarioRepository userRepository;
	@Autowired
	AmigoRepository friendRepository;
	@Autowired
	InvitacionAmistadRepository invitacionRepository;
	private static final Logger logger = Logger.getLogger("MyLog");

	@Autowired
	JwtUtils jwtUtils;

    @PostMapping("/send/friend-request")
	public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest addfriendRequest) {
		logger.info("user1=" + addfriendRequest.getUsername() + " user2=" + addfriendRequest.getFriendname());
		if ( (!userRepository.existsByUsername(addfriendRequest.getUsername())) ||(!userRepository.existsByUsername(addfriendRequest.getFriendname())) ) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Usuario o amigo no están registrados"));
		} else if(addfriendRequest.getUsername().equals(addfriendRequest.getFriendname())){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: No puedes ser amigo de tí mismo"));
		}else {
			logger.info("Restricciones cumplidas");
			Optional<Usuario> opUser = userRepository.findByUsername(addfriendRequest.getUsername());
			if(opUser.isPresent()){
				Usuario user = opUser.get();
				opUser = userRepository.findByUsername(addfriendRequest.getFriendname());
				if(opUser.isPresent()){
					Usuario user2 = opUser.get();
					user2.addInvitacion(user);
					userRepository.save(user2);
					return ResponseEntity.ok(new MessageResponse("Petición de amistad enviada a " + user2.getUsername()));
				} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede enviar la petición de amistad"));
			} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede enviar la petición de amistad"));
		}
	}

	@PostMapping("/receive/friend-request")
	public ResponseEntity<?> getInvitacionesAmistad(@RequestBody GetFriendRequest getfriendRequest) {
		logger.info("user1=" + getfriendRequest.getUsername() );
		if ( (!userRepository.existsByUsername(getfriendRequest.getUsername())) ) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No se pudo encontrar el usuario"));
		
		}else {
			logger.info("Restricciones cumplidas");
			Optional<Usuario> opUser = userRepository.findByUsername(getfriendRequest.getUsername());
			if(opUser.isPresent()){
				Usuario user = opUser.get();
				List<Usuario> inv = user.getInvitacion();
				logger.info("Se obtienen las peticiones de amistad" + inv);
				return ResponseEntity.ok(Sender.enviar(friendsToString(inv)));
			} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se pueden recuperar las peticiones de amistad."));
		}
	}

	private List<String> friendsToString(List<Usuario> l){
		List<String> l2 = new ArrayList<String>();
		for(Usuario u : l){
			l2.add(u.getUsername());
		}
		return l2;
	}
}
