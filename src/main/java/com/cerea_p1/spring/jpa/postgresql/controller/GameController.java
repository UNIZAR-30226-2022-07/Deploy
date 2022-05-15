package com.cerea_p1.spring.jpa.postgresql.controller;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;
import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.CreateGameRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.DeleteGameInvitation;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.DisconnectRequest;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.GetPartidas;
import com.cerea_p1.spring.jpa.postgresql.payload.request.game.InfoGame;
import com.cerea_p1.spring.jpa.postgresql.payload.response.InfoPartida;
import com.cerea_p1.spring.jpa.postgresql.payload.response.Jugada;
import com.cerea_p1.spring.jpa.postgresql.payload.response.MessageResponse;
import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;
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
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import com.cerea_p1.spring.jpa.postgresql.utils.Sender;
import com.google.gson.Gson;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;


@Slf4j
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200/", "https://one-fweb.herokuapp.com"})
@AllArgsConstructor
@Controller
public class GameController {
    private final GameService gameService = new GameService();

    @Autowired
	UsuarioRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger logger = Logger.getLogger("MyLog");

    @PostMapping("/game/create")
    public ResponseEntity<?> create(@RequestBody CreateGameRequest request) {
        logger.info("create game request by " + request.getPlayername() + ". NJugadores = "+request.getNPlayers()+". tTurn = "+request.getTTurn() + " reglas = " + request.getRules());
        List<Regla> l = new ArrayList<Regla>();
        boolean f = false;
        for(Regla r : request.getRules()){
            if(l.contains(r)) f = true;
            else l.add(r);
        }
        if(f){
            return ResponseEntity.badRequest().body(new MessageResponse("No puede haber reglas repetidas"));
        } else
            return ResponseEntity.ok(gameService.crearPartida(new Jugador(request.getPlayername()), request.getNPlayers(), request.getTTurn(), request.getRules()));
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
        System.out.println(getPartidas.getUsername());
        String s = gameService.getPartidasUser(getPartidas.getUsername());
        if(s == ""){
            return ResponseEntity.ok(new MessageResponse("No hay partidas para el usuario"));
        } else return ResponseEntity.ok(s);
    }

    @PostMapping("/game/getInfoPartida")
    public ResponseEntity<?> getInfoPartida(@RequestBody InfoGame idPartida){
        logger.info(idPartida.getIdPartida());
        if(gameService.existPartida(idPartida.getIdPartida())){
            
            Partida p = gameService.getPartida(idPartida.getIdPartida());
            List<String> j = new ArrayList<String>();
            for(Jugador g : p.getJugadores()){
                j.add(g.getNombre());
            }
            return ResponseEntity.ok(Sender.enviar(new InfoPartida(p.getNJugadores(), p.getTTurno(), j, p.getReglas())));
        } else return ResponseEntity.badRequest().body("Esa partida no existe");
    }

    @PostMapping("/game/invite")
    public ResponseEntity<?> inviteFriend(@RequestBody Invitacion invitacion){
        Optional<Usuario> opU = userRepository.findByUsername(invitacion.getUsername());
        if(opU.isPresent()){
            Usuario u = opU.get();
            Optional<Usuario> opF = userRepository.findByUsername(invitacion.getFriendname());
            if(opF.isPresent()){
                Usuario f = opF.get();
                if(u.getAmigos().contains(f)){
                    gameService.invitarAmigo(invitacion.getUsername(), invitacion.getFriendname(), invitacion.getGameId());
                    return ResponseEntity.ok(new MessageResponse("Amigo " + invitacion.getFriendname() + " invitado"));
                } else {
                    return ResponseEntity.badRequest().body(invitacion.getFriendname() + " no está entre tus amigos");
                }
            } else {
                return ResponseEntity.badRequest().body("No existe el usuario " + invitacion.getFriendname());
            }
            
        } else {
            return ResponseEntity.badRequest().body("No existe el usuario " + invitacion.getUsername());
        }
    }

    @PostMapping("/game/getInvitacionesPartida")
    public ResponseEntity<?> getInvitacionesPartida(@RequestBody GetPartidas request){
        logger.info("obtener las invitaciones a partida de " + request.getUsername());
        Optional<Usuario> opU = userRepository.findByUsername(request.getUsername());
        if(opU.isPresent()){
            logger.info("Busco las invitaciones a partida de " + request.getUsername());
            return ResponseEntity.ok(Sender.enviar(gameService.getInvitacionesPartida(request.getUsername())));
        } else {
            return ResponseEntity.badRequest().body("No existe el usuario " + request.getUsername());
        }
    }

    @PostMapping("/game/cancelarInvitacionPartida")
    public ResponseEntity<?> deleteInvitacionPartida(@RequestBody DeleteGameInvitation request){
        logger.info("cancelo la invitacion a la partida " + request.getUsername() + " " + request.getGameId());
        Optional<Usuario> opU = userRepository.findByUsername(request.getUsername());
        if(opU.isPresent()){
            if(gameService.deleteGameInvitation(request.getUsername(), request.getGameId()))
            return ResponseEntity.ok(new MessageResponse("Se ha eliminado la invitación a partida"));
            else return ResponseEntity.ok(new MessageResponse("No se ha podido eliminar la invitación a partida"));
        } else {
            return ResponseEntity.badRequest().body("No existe el usuario " + request.getUsername());
        }
    }

    @PostMapping("/game/getPartidaPublica")
    public ResponseEntity<?> getPartidaPublica(){
        logger.info("buscando partida publica");
        return ResponseEntity.ok(Sender.enviar(gameService.getPartidaPublica().getId()));
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
            System.out.println(Numero.CERO);
            logger.info("begin game request by " + username);
            //ENVIAR MANOS INICIALES A TODOS LOS JUGADORES
            Partida game = gameService.beginGame(roomId);
            for(Jugador j : game.getJugadores()){
                logger.info("send to " + j.getNombre());
                simpMessagingTemplate.convertAndSendToUser(j.getNombre(), "/msg", j.getCartas());
                System.out.println(j.getNombre() + " " + j.getCartas());
            }

            return Sender.enviar(game.getUltimaCartaJugada());
        } catch(Exception e) {
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e.getMessage());
        }
    }

    @MessageMapping("/disconnect/{roomId}")
    @SendTo("/topic/disconnect/{roomId}")
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
    public String card(@DestinationVariable("roomId") String roomId,@Payload String c, @Header("username") String username) {
        try{
            Carta carta = new Gson().fromJson(c, Carta.class);
            logger.info(carta.getNumero()+" "+carta.getColor()+ " played by "+ username);
            Partida p = gameService.getPartida(roomId);
            logger.info("Es el turno de" + p.getTurno().getNombre());
            if(!p.getTurno().getNombre().equals(username)){
                simpMessagingTemplate.convertAndSendToUser(username, "/msg", "No es tu turno");
                return Sender.enviar(new String("ALGUIEN HA INTENTADO JUGAR Y NO ERA SU TURNO"));
            }
            Jugada j = gameService.playCard(roomId, new Jugador(username), carta);
            Jugador jugador = p.getJugador(new Jugador(username));
            if(jugador.getCartas().size() == 0){
                Optional<Usuario> opU = userRepository.findByUsername(jugador.getNombre());
                Usuario u = opU.get();
                u.setPuntos(u.getPuntos()+20);
                userRepository.save(u);
                for(Jugador jj : p.getJugadores()){
                    if(!jj.getNombre().equals(jugador.getNombre())){
                        opU = userRepository.findByUsername(jj.getNombre());
                        u = opU.get();
                        if(u.getPuntos()-5>0)
                        u.setPuntos(u.getPuntos()-5);
                        else u.setPuntos(0);
                        userRepository.save(u);
                    }
                }
                gameService.finJuego(roomId);
                return Sender.enviar(new String("HA GANADO " + jugador.getNombre()));
            }

            return Sender.enviar(j);
        } catch(Exception e){
            logger.warning("Exception" + e.getMessage());
            return Sender.enviar(e);
        }
    }

    @MessageMapping("/pasarTurno/{roomId}")
    @SendTo("/topic/jugada/{roomId}")
    @MessageExceptionHandler()
    public String pasarTurno(@DestinationVariable("roomId") String roomId, @Header("username") String username) {
        try{
           
            Partida p = gameService.getPartida(roomId);
            if(!p.getTurno().getNombre().equals(username)){
                simpMessagingTemplate.convertAndSendToUser(username, "/msg", "No es tu turno");
                return Sender.enviar(new String("ALGUIEN HA INTENTADO JUGAR Y NO ERA SU TURNO"));
            }
            p.siguienteTurno();
            Jugador jugador = p.getJugador(new Jugador(username));
            if(jugador.getCartas().size() == 0){
                Optional<Usuario> opU = userRepository.findByUsername(jugador.getNombre());
                Usuario u = opU.get();
                u.setPuntos(u.getPuntos()+20);
                userRepository.save(u);
                for(Jugador jj : p.getJugadores()){
                    if(!jj.getNombre().equals(jugador.getNombre())){
                        opU = userRepository.findByUsername(jj.getNombre());
                        u = opU.get();
                        if(u.getPuntos()-5>0)
                        u.setPuntos(u.getPuntos()-5);
                        else u.setPuntos(0);
                        userRepository.save(u);
                    }
                }
                gameService.finJuego(roomId);
                return Sender.enviar(new String("HA GANADO " + jugador.getNombre()));
            }

            return Sender.enviar(new Jugada(p.getUltimaCartaJugada(),p.getJugadores(), p.getTurno().getNombre()));
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
            if(!game.getTurno().getNombre().equals(username)){
                simpMessagingTemplate.convertAndSendToUser(username, "/msg", "No es tu turno");
                return Sender.enviar(new String("ALGUIEN HA INTENTADO JUGAR Y NO ERA SU TURNO"));
            }
            //Enviar cartas robadas al solicitante
            Jugador jugador = game.getJugador(new Jugador(username));
            simpMessagingTemplate.convertAndSendToUser(username, "/msg", gameService.drawCards(roomId, jugador, nCards));
            
            return Sender.enviar(new Jugada(game.getUltimaCartaJugada(), game.getJugadores(), game.getTurno().getNombre()));
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