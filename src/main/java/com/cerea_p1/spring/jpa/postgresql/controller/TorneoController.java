package com.cerea_p1.spring.jpa.postgresql.controller;

import java.util.logging.Logger;

import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.security.services.TorneoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200/", "https://one-fweb.herokuapp.com"})
@AllArgsConstructor
@Controller
public class TorneoController {
    TorneoService torneoService = new TorneoService();

    @Autowired
	UsuarioRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger logger = Logger.getLogger("MyLog");

    // @PostMapping("/torneo/createTorneo")
    // public ResponseEntity<?> crearTorneo(){
        
    // }
}
