package com.cerea_p1.spring.jpa.postgresql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cerea_p1.spring.jpa.postgresql.payload.request.rankings.RankingAmigosRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.rankings.RankingPaisRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;

import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;

import com.cerea_p1.spring.jpa.postgresql.security.jwt.JwtUtils;

import com.cerea_p1.spring.jpa.postgresql.utils.Sender;


import java.util.logging.*;


@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200/","https://one-fweb.herokuapp.com"})
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
	UsuarioRepository userRepository;
	private static final Logger logger = Logger.getLogger("MyLog");

	@Autowired
	JwtUtils jwtUtils;

    @PostMapping("/rankingPais")
    @MessageExceptionHandler()
	public ResponseEntity<?> rankingPorPais(@RequestBody RankingPaisRequest paisRequest) {
        try{
            return ResponseEntity.ok(new MessageResponse(Sender.enviar(userRepository.userRankingByPais(paisRequest.getPais()))));
        }catch (Exception e){
            logger.warning("Exception" + e.getMessage());
            return ResponseEntity.badRequest().body(Sender.enviar(e.getMessage()));
        }
	}

    @PostMapping("/rankingMundial")
    @MessageExceptionHandler()
	public ResponseEntity<?> rankingPorMundial() {
        try{
            return ResponseEntity.ok(new MessageResponse(Sender.enviar(userRepository.userRankingMundial())));
        }catch (Exception e){
            logger.warning("Exception" + e.getMessage());
            return ResponseEntity.badRequest().body(Sender.enviar(e.getMessage()));
        }
	}

    @PostMapping("/rankingAmigos")
    @MessageExceptionHandler()
	public ResponseEntity<?> rankingPorAmigos(@RequestBody RankingAmigosRequest amigosRequest) {
        logger.info("user = " + amigosRequest.getUsername());
        try{
            return ResponseEntity.ok(new MessageResponse(Sender.enviar(userRepository.userRankingAmigos(amigosRequest.getUsername()))));
        }catch (Exception e){
            logger.warning("Exception" + e.getMessage());
            return ResponseEntity.badRequest().body(Sender.enviar(e.getMessage()));
        }
	}
}
