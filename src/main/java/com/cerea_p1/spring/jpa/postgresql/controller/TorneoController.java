package com.cerea_p1.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;
import com.cerea_p1.spring.jpa.postgresql.model.game.Torneo;
import com.cerea_p1.spring.jpa.postgresql.payload.request.torneo.CrearTorneo;
import com.cerea_p1.spring.jpa.postgresql.payload.response.torneo.InfoTorneoResponse;
import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.security.services.TorneoService;
import com.cerea_p1.spring.jpa.postgresql.utils.Sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/torneo/createTorneo")
    public ResponseEntity<?> crearTorneo(@RequestBody CrearTorneo request){
        logger.info("crear un torneo");
        Torneo t = torneoService.crearTorneo(new Jugador(request.getUsername()), request.getTiempoTurno(), request.getReglas());
        List<String> s = new ArrayList<String>();
        for(Jugador j : t.getJugadores()){
            s.add(j.getNombre());
        }
        return ResponseEntity.ok(Sender.enviar(new InfoTorneoResponse(t.getIdTorneo(), t.getTiempoTurno(), s, t.getReglas())));
    }

    @PostMapping("/torneo/getTorneos")
    public ResponseEntity<?> getTorneos(){
        return ResponseEntity.ok(Sender.enviar(torneoService.listaTorneos()));
    }
}
