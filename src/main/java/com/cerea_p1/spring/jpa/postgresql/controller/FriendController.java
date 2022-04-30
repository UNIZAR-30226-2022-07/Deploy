package com.cerea_p1.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

import com.cerea_p1.spring.jpa.postgresql.payload.request.friends.AddFriendRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.friends.GetFriendRequest;

import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;

import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;

import com.cerea_p1.spring.jpa.postgresql.security.jwt.JwtUtils;

import com.cerea_p1.spring.jpa.postgresql.utils.Sender;


import java.util.logging.*;


@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
	UsuarioRepository userRepository;
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
					if (!user.getInvitacion().contains(user2) && !user.getAmigos().contains(user2) && !user.getInvitacionesEnviadas().contains(user2)){
						user2.addInvitacion(user);
						userRepository.save(user2);
						return ResponseEntity.ok(new MessageResponse("Petición de amistad enviada a " + user2.getUsername()));
					} else return ResponseEntity.badRequest().body(new MessageResponse("Error: " + user2.getUsername() + " ya forma parte de tus amigos o ya te envió una petición de amistad"));
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

	@PostMapping("/accept/friend-request")
	public ResponseEntity<?> acceptInvitacionesAmistad(@RequestBody AddFriendRequest acceptfriendRequest) {
		logger.info("user1=" + acceptfriendRequest.getUsername() + " user2=" + acceptfriendRequest.getFriendname());
		if ( (!userRepository.existsByUsername(acceptfriendRequest.getUsername())) ||(!userRepository.existsByUsername(acceptfriendRequest.getFriendname())) ) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Usuario o amigo no están registrados"));
		} else {
			logger.info("Restricciones cumplidas");
			Optional<Usuario> opUser = userRepository.findByUsername(acceptfriendRequest.getUsername());
			if(opUser.isPresent()){
				Usuario user = opUser.get();
				opUser = userRepository.findByUsername(acceptfriendRequest.getFriendname());
				if(opUser.isPresent()){
					Usuario user2 = opUser.get();
					if (user.getInvitacion().contains(user2)){
						user.removeInvitacion(user2);
						user.addAmigo(user2);
						user2.addAmigo(user);
						userRepository.save(user);
						userRepository.save(user2);
						return ResponseEntity.ok(new MessageResponse("Amigo añadido: " + user2.getUsername()));
					} else return ResponseEntity.badRequest().body(new MessageResponse("Error: " + user2.getUsername() + " no se encuentra entre tus peticiones de amistad."));
				} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede aceptar la petición de amistad"));
			} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede aceptar la petición de amistad"));
		}
	}


	@PostMapping("/friendsList")
	public ResponseEntity<?> getAmigos(@RequestBody GetFriendRequest getfriendRequest) {
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
				List<Usuario> inv = user.getAmigos();
				logger.info("Se obtienen los amigos amistad" + inv);
				return ResponseEntity.ok(Sender.enviar(friendsToString(inv)));
			} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se pueden recuperar los amigos."));
		}
	}

	@PostMapping("/cancel/friend-request")
	public ResponseEntity<?> cancelInvitacionesAmistad(@RequestBody AddFriendRequest acceptfriendRequest) {
		logger.info("user1=" + acceptfriendRequest.getUsername() + " user2=" + acceptfriendRequest.getFriendname());
		if ( (!userRepository.existsByUsername(acceptfriendRequest.getUsername())) ||(!userRepository.existsByUsername(acceptfriendRequest.getFriendname())) ) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Usuario o amigo no están registrados"));
		} else {
			logger.info("Restricciones cumplidas");
			Optional<Usuario> opUser = userRepository.findByUsername(acceptfriendRequest.getUsername());
			if(opUser.isPresent()){
				Usuario user = opUser.get();
				opUser = userRepository.findByUsername(acceptfriendRequest.getFriendname());
				if(opUser.isPresent()){
					Usuario user2 = opUser.get();
					if (user.getInvitacion().contains(user2)){
						user.removeInvitacion(user2);
						userRepository.save(user);
						return ResponseEntity.ok(new MessageResponse("Petición de amistad cancelada: " + user2.getUsername()));
					} else return ResponseEntity.badRequest().body(new MessageResponse("Error: " + user2.getUsername() + " no se encuentra entre tus peticiones de amistad."));
				} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede cancelar la petición de amistad"));
			} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede cancelar la petición de amistad"));
		}
	}

	@PostMapping("/deleteFriend")
	public ResponseEntity<?> deleteAmistad(@RequestBody AddFriendRequest acceptfriendRequest) {
		logger.info("user1=" + acceptfriendRequest.getUsername() + " user2=" + acceptfriendRequest.getFriendname());
		if ( (!userRepository.existsByUsername(acceptfriendRequest.getUsername())) ||(!userRepository.existsByUsername(acceptfriendRequest.getFriendname())) ) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Usuario o amigo no están registrados"));
		} else {
			logger.info("Restricciones cumplidas");
			Optional<Usuario> opUser = userRepository.findByUsername(acceptfriendRequest.getUsername());
			if(opUser.isPresent()){
				Usuario user = opUser.get();
				opUser = userRepository.findByUsername(acceptfriendRequest.getFriendname());
				if(opUser.isPresent()){
					Usuario user2 = opUser.get();
					if (user.getAmigos().contains(user2)){
						user.removeAmigo(user2);
						user2.removeAmigo(user);
						userRepository.save(user);
						userRepository.save(user2);
						return ResponseEntity.ok(new MessageResponse("Amigo eliminado: " + user2.getUsername()));
					} else return ResponseEntity.badRequest().body(new MessageResponse("Error: " + user2.getUsername() + " no se encuentra entre tus amigos."));
				} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede eliminar a tu amigo"));
			} else return ResponseEntity.badRequest().body(new MessageResponse("Error: No se puede eliminar a tu amigo"));
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
