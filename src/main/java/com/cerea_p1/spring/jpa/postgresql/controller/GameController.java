package com.cerea_p1.spring.jpa.postgresql.controller;

import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.CreateGameRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.DisconnectRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.GetPartidas;
import com.cerea_p1.spring.jpa.postgresql.payload.response.Jugada;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;
import com.cerea_p1.spring.jpa.postgresql.security.services.GameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import java.util.logging.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import com.cerea_p1.spring.jpa.postgresql.utils.Sender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;


@Slf4j
@CrossOrigin(allowCredentials = "true", origins = "http://localhost:4200/")
@AllArgsConstructor
@Controller
public class GameController {
    private final GameService gameService = new GameService();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger logger = Logger.getLogger("MyLog");

    @PostMapping("/game/create")
    public ResponseEntity<Partida> create(@RequestBody CreateGameRequest request) {
        logger.info("create game request by " + request.getPlayername() + ". NJugadores = "+request.getNPlayers()+". tTurn = "+request.getTTurn());
        return ResponseEntity.ok(gameService.crearPartida(new Jugador(request.getPlayername()), request.getNPlayers(), request.getTTurn()));
    }

    @PostMapping("/game/getCartas")
    public ResponseEntity<?> getCartas(@RequestBody DisconnectRequest getCartas){
        Partida p = gameService.getPartida(getCartas.getGameId());
        if ( p == null){
            return ResponseEntity.badRequest().body(new MessageResponse("La partida no existe"));
        } else {
            if(p.playerAlreadyIn(new Jugador(getCartas.getPlayerName())))
                return ResponseEntity.ok(Sender.enviar(p.getJugador(new Jugador(getCartas.getPlayerName())).getCartas()));
            else 
                return ResponseEntity.badRequest().body(new MessageResponse("El jugador no está en la partida"));
        }
    }

    @PostMapping("/game/getPartidasActivas")
    public ResponseEntity<?> getPartidas(@RequestBody GetPartidas getPartidas){
        String s = gameService.getPartidasUser(getPartidas.getUsername());
        if(s == ""){
            return ResponseEntity.badRequest().body(new MessageResponse("No hay partidas para el usuario"));
        } else return ResponseEntity.ok(s);
    }

    @MessageMapping("/connect/{roomId}")
	@SendTo("/topic/connect/{roomId}")
    @MessageExceptionHandler()
    public String connect(@DestinationVariable("roomId") String roomId, @Header("username") String username) {
        try{
			logger.info("connect request by " + username);
			return Sender.enviar(gameService.connectToGame(new Jugador(username), roomId));
        } catch(Exception e) {
            logger.warning("Exception" + e.getMessage());
          	return Sender.enviar(e);
        }
    }

    @MessageMapping("/begin/{roomId}")
	@SendTo("/topic/begin/{roomId}")
    @MessageExceptionHandler()
    public String begin(@DestinationVariable("roomId") String roomId, @Header("username") String username) {
        try{
            logger.info("begin game request by " + username);
            //ENVIAR MANOS INICIALES A TODOS LOS JUGADORES
            Partida game = gameService.beginGame(roomId);
            for(Jugador j : game.getJugadores()){
                logger.info("send to " + j.getNombre());
                simpMessagingTemplate.convertAndSendToUser(j.getNombre(), "/msg", j.getCartas());
            }
            logger.info(Sender.enviar(game.getUltimaCartaJugada()));
            return Sender.enviar(game.getUltimaCartaJugada());
        } catch(Exception e) {
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e.getMessage());
        }
    }

    @MessageMapping("/disconnect/{roomId}")
    @SendTo("/topic/diconnect/{roomId}")
    @MessageExceptionHandler()
    public String disconnect(@DestinationVariable("roomId") String roomId, @Header("username") String username) {
        try{
            logger.info("disconnect request by " + username);
            return Sender.enviar(gameService.disconnectFromGame(roomId, new Jugador(username)));
        } catch(Exception e){
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e);
        }
    }

    @MessageMapping("/card/play/{roomId}")
    @SendTo("/topic/jugada/{roomId}")
    @MessageExceptionHandler()
    public String card(@DestinationVariable("roomId") String roomId, @Header("username") String username, @RequestBody Carta c) {
        try{
            logger.info(c.getNumero()+" "+c.getColor()+ " played by "+ username);

            Partida game = gameService.getPartida(roomId);
            // for(Jugador j : game.getJugadores()){
            //     logger.info("send to " + j.getNombre());
            //     simpMessagingTemplate.convertAndSendToUser(j.getNombre(), "/msg", "Siguiente turno");
            // }

            return Sender.enviar(gameService.playCard(roomId, new Jugador(username), c));
        } catch(Exception e){
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e);
        }
    }

    @MessageMapping("/card/draw/{roomId}")
    @SendTo("/topic/jugada/{roomId}")
    @MessageExceptionHandler()
    public String draw(@DestinationVariable("roomId") String roomId, @Header("username") String username, @RequestBody int nCards) {
        try{
            logger.info(nCards+" drawn by " + username);

            Partida game = gameService.getPartida(roomId);
            //Enviar cartas robadas al solicitante
            simpMessagingTemplate.convertAndSendToUser(username, "/msg", gameService.drawCards(roomId, new Jugador(username), nCards));
            // //Enviar mensaje siguiente turno
            // for(Jugador j : game.getJugadores()){
            //     logger.info("send to " + j.getNombre());
            //     simpMessagingTemplate.convertAndSendToUser(j.getNombre(), "/msg", "Siguiente turno");
            // }
            return Sender.enviar(new Jugada(game.getUltimaCartaJugada(), game.getJugadores()));
        } catch(Exception e){
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e);
        }
    }

    @MessageMapping("/message/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    @MessageExceptionHandler()
    public String sendMessage(@DestinationVariable("roomId") String roomId, @Header("username") String username, @RequestBody String message) {
        try{
            logger.info(message + " sent to chat by " + username);
            return Sender.enviar(new EmisorChat(username, message));
        } catch(Exception e){
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e);
        }
    }

    // @ExceptionHandler(Exception.class)
    // public ModelAndView handleException(NullPointerException ex)
    // {
    //     //Do something additional if required
    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("error");
    //     modelAndView.addObject("message", ex.getMessage());
    //     return modelAndView;
    // }

    /*@PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody Player player) throws GameException{
        log.info("connect random {}", player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }*/

  /*  @PostMapping("/sow")
    public ResponseEntity<Partida> sow(@RequestBody Sow sow) throws GameException {
        logger.info("sow: {}", sow);
        Partida game = gameService.sow(sow);

        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getId(), game);
        return ResponseEntity.ok(game);
    }*/
}