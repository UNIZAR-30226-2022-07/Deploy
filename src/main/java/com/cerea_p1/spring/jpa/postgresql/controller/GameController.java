package com.cerea_p1.spring.jpa.postgresql.controller;

import com.cerea_p1.spring.jpa.postgresql.exception.GameException;
import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.CreateGameRequest;
import com.cerea_p1.spring.jpa.postgresql.security.services.GameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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


@Slf4j
@CrossOrigin(allowCredentials = "true", origins = "http://localhost:4200/") // con asterisco no funciona
@AllArgsConstructor
@Controller
public class GameController {
    private final GameService gameService = new GameService();
    private final SimpMessagingTemplate simpMessagingTemplate = null;

    private static final Logger logger = Logger.getLogger("MyLog");

    @PostMapping("/game/create")
    public ResponseEntity<Partida> create(@RequestBody CreateGameRequest request) {
        logger.info("create game request by " + request.getPlayername() + ". NJugadores = "+request.getNPlayers()+". tTurn = "+request.getTTurn());
        return ResponseEntity.ok(gameService.crearPartida(new Jugador(request.getPlayername()), request.getNPlayers(), request.getTTurn()));
    }

    @MessageMapping("/connect/{roomId}")
	@SendTo("/topic/game/{roomId}")
    @ExceptionHandler(GameException.class)
    public String connect(@DestinationVariable("roomId") String roomId, @Header("username") String username) { 
        try{
			logger.info("connect request by " + username);
			return Sender.enviar(gameService.connectToGame(new Jugador(username), roomId));
        } catch(GameException e) {
          	return Sender.enviar(e);
        }
    }


    @MessageMapping("/game/begin")
	@SendTo("/topic/game/{roomId}")
    @ExceptionHandler(GameException.class)
    public String begin(@DestinationVariable("roomId") String roomId, @RequestParam("username") String username) throws GameException {
        try{
            logger.info("begin game request by " + username);
            gameService.beginGame(roomId);
            // ENVIAR MANOS INICIALES A TODOS LOS JUGADORES
            Partida game = gameService.beginGame(roomId);
            for(Jugador j : game.getJugadores()){
                simpMessagingTemplate.convertAndSendToUser(j.getNombre(), "/game", j.getCartas());
            }
            return Sender.enviar(game.getCartaInicial());
        } catch(GameException e) {
            return Sender.enviar(e);
        }
    }

    @MessageMapping("/disconnect/{roomId}")
    @SendTo("/topic/game/{roomId}")
    @ExceptionHandler(GameException.class)
    public String disconnect(@DestinationVariable("roomId") String roomId, @Header("username") String username) {
        try{
            logger.info("disconnect request by " + username);
            return Sender.enviar(gameService.disconnectFromGame(new Jugador(username), roomId));
        } catch(GameException e){
            return Sender.enviar(e);
        }
    }

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