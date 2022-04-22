package com.cerea_p1.spring.jpa.postgresql.controller;

//import ex.com.challenge.dto.ConnectRequest;
import com.cerea_p1.spring.jpa.postgresql.exception.GameException;
import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.CreateGameRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.ConnectRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.DisconnectRequest;
import com.cerea_p1.spring.jpa.postgresql.security.services.GameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService = new GameService();
    private final SimpMessagingTemplate simpMessagingTemplate = null ;

    private static final Logger logger = Logger.getLogger("MyLog");

    @PostMapping("/create")
    public ResponseEntity<Partida> crear(@RequestBody CreateGameRequest request) {
        logger.info("create game request by " + request.getPlayerName());
        return ResponseEntity.ok(gameService.crearPartida(new Jugador(request.getPlayerName())));
    }

    @PostMapping("/connect")
  //  @ExceptionHandler(GameException.class)
    @ExceptionHandler(GameException.class)
    public ResponseEntity<?> connect(@RequestBody ConnectRequest request) throws GameException {
        try{
            logger.info("connect request by " + request.getPlayerName());
            return ResponseEntity.ok(gameService.connectToGame(new Jugador(request.getPlayerName()), request.getGameId()));
        } catch(GameException e){
        //     return new ResponseEntity.badRequest();
             return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/disconnect")
    @ExceptionHandler(GameException.class)
    public ResponseEntity<?> disconnect(@RequestBody DisconnectRequest request) throws GameException {
        try{
            logger.info("disconnect request by " + request.getPlayerName());
            gameService.disconnectFromGame(new Jugador(request.getPlayerName()), request.getGameId());
            return new ResponseEntity<String>("OK",HttpStatus.OK);
        } catch(GameException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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