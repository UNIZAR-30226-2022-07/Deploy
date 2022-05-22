package com.cerea_p1.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;
import com.cerea_p1.spring.jpa.postgresql.model.game.Partida;
import com.cerea_p1.spring.jpa.postgresql.model.game.Torneo;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.GetPartidas;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.JugarCarta;
import com.cerea_p1.spring.jpa.postgresql.payload.request.torneo.CheckSemifinal;
import com.cerea_p1.spring.jpa.postgresql.payload.request.torneo.CrearTorneo;
import com.cerea_p1.spring.jpa.postgresql.payload.request.torneo.JugarFinal;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;
import com.cerea_p1.spring.jpa.postgresql.payload.response.torneo.InfoTorneoResponse;
import com.cerea_p1.spring.jpa.postgresql.payload.response.PartidasResponse;
import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
import com.cerea_p1.spring.jpa.postgresql.security.services.TorneoService;
import com.cerea_p1.spring.jpa.postgresql.utils.Sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
        List<InfoTorneoResponse> listaInfoTorneos = new ArrayList<InfoTorneoResponse>();
        for(Torneo t : torneoService.listaTorneos()) {
            List<String> s = new ArrayList<String>();
            for(Jugador j : t.getJugadores()){
                s.add(j.getNombre());
            }
            listaInfoTorneos.add(new InfoTorneoResponse(t.getIdTorneo(), t.getTiempoTurno(), s, t.getReglas()));
        }
        return ResponseEntity.ok(Sender.enviar(listaInfoTorneos));
    }

    @PostMapping("/torneo/jugarFinal")
    public ResponseEntity<?> jugarFinal(@RequestBody JugarFinal request){
        logger.info("Finalista " + request.getUsername());
        return ResponseEntity.ok(torneoService.jugarFinal(request.getUsername(), request.getTorneoId()));
    }

    @PostMapping("/torneo/game/isSemifinal")
    public ResponseEntity<?> isSemifinal(@RequestBody CheckSemifinal request){
        if(torneoService.existeTorneo(request.getIdTorneo())){
            boolean isSemifinal = torneoService.isSemifinal(request.getIdPartida(), request.getIdTorneo());
            return ResponseEntity.ok(Sender.enviar(isSemifinal));
        } else return ResponseEntity.badRequest().body("Ese torneo no existe");
    }

    @PostMapping("/torneo/getTorneosActivos")
    public ResponseEntity<?> getPartidas(@RequestBody GetPartidas request){
    //    System.out.println(getPartidas.getUsername());
        String s = torneoService.getTorneosUser(request.getUsername());
        if(s == ""){
            return ResponseEntity.ok(new MessageResponse("No hay partidas para el usuario"));
        } else return ResponseEntity.ok(new PartidasResponse(s));
    }

    @MessageMapping("/connect/torneo/{torneoId}")
	@SendTo("/topic/connect/torneo/{torneoId}")
    @MessageExceptionHandler()
    public String connect(@DestinationVariable("torneoId") String roomId, @Header("username") String username) {
        try{
			logger.info("tournament connect request by " + username);
			return Sender.enviar(torneoService.connectToTorneo(new Jugador(username), roomId));
        } catch(Exception e) {
            logger.warning("Exception" + e.getMessage());
          	return Sender.enviar(e);
        }
    }

    @MessageMapping("/disconnect/torneo/{torneoId}")
	@SendTo("/topic/disconnect/torneo/{torneoId}")
    @MessageExceptionHandler()
    public String disconnect(@DestinationVariable("torneoId") String roomId, @Header("username") String username) {
        try{
			logger.info("tournament connect request by " + username);
			return Sender.enviar(torneoService.disconnectTorneo(roomId,username));
        } catch(Exception e) {
            logger.warning("Exception" + e.getMessage());
          	return Sender.enviar(e);
        }
    }

    @MessageMapping("/begin/torneo/{torneoId}")
    @MessageExceptionHandler()
    public void begin(@DestinationVariable("torneoId") String torneoId, @Header("username") String username) {
        try{
            logger.info("begin tournament request by " + username);
            Torneo torneo = torneoService.beginTorneo(torneoId);
            List<Partida> partidas_torneo = torneo.getPartidas();
            if(partidas_torneo == null) {
                logger.info("no hay partidas de torneo");
            }
            for(Partida p : partidas_torneo) {
                logger.info("partida torneo"+p.toString());
            }
            List<Jugador> jugadores = torneo.getJugadores();
            for(Jugador j : jugadores) {
                logger.info("jugador torneo"+j.getNombre());
            }
            for(int j = 0; j<torneo.getNJugadores(); ++j){
                logger.info("send to " + jugadores.get(j).getNombre());

                // enviar a cada jugador la partida correspondiente
                logger.info("semifinal: "+partidas_torneo.get(j/3));
                simpMessagingTemplate.convertAndSendToUser(jugadores.get(j).getNombre(), "/msg", partidas_torneo.get(j/3));
                logger.info(jugadores.get(j).getNombre() + " " + partidas_torneo.get(j/3));
            }
        } catch(Exception e) {
            logger.warning("Exception" + e.getMessage());
        }
    }
}
