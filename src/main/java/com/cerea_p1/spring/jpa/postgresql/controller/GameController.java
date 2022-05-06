package com.cerea_p1.spring.jpa.postgresql.controller;

import com.cerea_p1.spring.jpa.postgresql.exception.*;
import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.CreateGameRequest;
import com.cerea_p1.spring.jpa.postgresql.security.services.GameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
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

    @MessageMapping("/connect/{roomId}")
	@SendTo("/topic/game/{roomId}")
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
	@SendTo("/topic/game/{roomId}")
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
            return Sender.enviar(game.getUltimaCartaJugada());
        } catch(Exception e) {
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e.getMessage());
        }
    }

    @MessageMapping("/disconnect/{roomId}")
    @SendTo("/topic/game/{roomId}")
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
    @SendTo("/topic/game/{roomId}")
    @MessageExceptionHandler()
    public String card(@DestinationVariable("roomId") String roomId, @Header("username") String username, @RequestBody Carta c) {
        try{
            logger.info(c.getNumero()+" "+c.getColor()+ " played by "+ username);

            Partida game = gameService.getPartida(roomId);
            for(Jugador j : game.getJugadores()){
                logger.info("send to " + j.getNombre());
                simpMessagingTemplate.convertAndSendToUser(j.getNombre(), "/msg", "Siguiente turno");
            }

            return Sender.enviar(gameService.playCard(roomId, new Jugador(username), c));
        } catch(Exception e){
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e);
        }
    }

    @MessageMapping("/card/draw/{roomId}")
    @SendTo("/topic/game/{roomId}")
    @MessageExceptionHandler()
    public String draw(@DestinationVariable("roomId") String roomId, @Header("username") String username, @RequestBody int nCards) {
        try{
            logger.info(nCards+" drawn by " + username);

            Partida game = gameService.getPartida(roomId);
            //Enviar mensaje siguiente turno
            for(Jugador j : game.getJugadores()){
                logger.info("send to " + j.getNombre());
                simpMessagingTemplate.convertAndSendToUser(j.getNombre(), "/msg", "Siguiente turno");
            }
            //Enviar cartas robadas al solicitante
            simpMessagingTemplate.convertAndSendToUser(username, "/msg", gameService.drawCards(roomId, new Jugador(username), nCards));
            return "cartas robadas por "+ username;
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