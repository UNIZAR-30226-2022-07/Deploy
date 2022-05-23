package com.cerea_p1.spring.jpa.postgresql.security.services;

import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.response.Jugada;
import com.cerea_p1.spring.jpa.postgresql.exception.*;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class GameService {

    private ConcurrentHashMap<String,Partida> almacen_partidas;
    private ConcurrentHashMap<String,List<Invitacion_almacen>> almacen_invitaciones;

    public GameService(){
        almacen_partidas = new ConcurrentHashMap<String,Partida>();
        almacen_invitaciones = new ConcurrentHashMap<String,List<Invitacion_almacen>>();
    }

    public Partida getPartida(String gameId) {
        return almacen_partidas.get(gameId);
    }

    public boolean existPartida(String gameId){
        return almacen_partidas.containsKey(gameId);
    }

    public Partida crearPartida(Jugador jugador,int nJugadores, int tTurno, List<Regla> reglas) {
        Partida game = new Partida(true);
        game.setId(UUID.randomUUID().toString());
        game.addJugador(jugador);
        game.setEstado(EstadoPartidaEnum.NEW);
        game.setNJugadores(nJugadores);
        game.setTTurno(tTurno);
        game.setReglas(reglas);
        almacen_partidas.put(game.getId(),game);
        return game;
    }

    public void addPartida(Partida p){
        almacen_partidas.put(p.getId(),p);
    }

    public Partida crearPartidaPublica() {
        Partida game = new Partida(false);
        game.setId(UUID.randomUUID().toString());
        game.setEstado(EstadoPartidaEnum.NEW);
        game.setNJugadores(4);
        game.setTTurno(20);
        almacen_partidas.put(game.getId(),game);
        return game;
    }

    public Partida getPartidaPublica(){
        for(Partida p : almacen_partidas.values()){
            if(!p.getTipo() && p.getEstado() == EstadoPartidaEnum.NEW && p.getJugadores().size() < p.getNJugadores()){
                return p;
            }
        }
        return crearPartidaPublica();
    }

    public List<Jugador> connectToGame(Jugador player, String gameId) {
        if(player != null){
            Optional<Partida> optionalGame;
            if(almacen_partidas.containsKey(gameId))
                optionalGame = Optional.of(almacen_partidas.get(gameId));
            else { 
                optionalGame = null; 
                throw new ConnectGameException("Esa partida no existe"); 
            }

            optionalGame.orElseThrow(() -> new ConnectGameException("Game with provided id doesn't exist"));

            Partida game = optionalGame.get();
            if(game.getJugadores().size() >= game.getNJugadores()) 
                throw new ConnectGameException("Partida llena.");
            if(!game.playerAlreadyIn(player))
                game.addJugador(player);
            return game.getJugadores();
        } else
            throw new ConnectGameException("Jugador no valido");
    }

    public String disconnectFromGame(String gameId, Jugador player){
        if(player != null) {
            Optional<Partida> optionalGame;
            if(almacen_partidas.containsKey(gameId))
                optionalGame = Optional.of(almacen_partidas.get(gameId));
            else {
                optionalGame = null; 
                throw new DisconnectGameException("Esa partida no existe");
            }

            optionalGame.orElseThrow(() -> new DisconnectGameException("Game with provided id doesn't exist"));

            Partida game = optionalGame.get();
            if(game.getEstado() != EstadoPartidaEnum.NEW){
                throw new DisconnectGameException("No puedes salir de la partida.");
            }

            if(game.playerAlreadyIn(player)) {
                game.removePlayer(player);

                if(game.getJugadores().size() == 0)
                    almacen_partidas.remove(gameId);

                return player.getNombre()+" disconnected successfully from "+ game.getId();
            }
            else
                throw new DisconnectGameException("Jugador no pertenece a la partida");
                
        } else
            throw new DisconnectGameException("Jugador no valido");
    }

    public Partida beginGame(String gameId){
        Optional<Partida> optionalGame;
        if(almacen_partidas.containsKey(gameId))
            optionalGame = Optional.of(almacen_partidas.get(gameId));
        else { 
            optionalGame = null;
            throw new BeginGameException("Esa partida no existe");
        }

        optionalGame.orElseThrow(() -> new BeginGameException("Game with provided id doesn't exist"));

        Partida game = optionalGame.get();
        if(game.getEstado() == EstadoPartidaEnum.NEW){
            game.setEstado(EstadoPartidaEnum.IN_PROGRESS);
        }
        // se eliminan las invitaciones a esa partida
        for(String s : almacen_invitaciones.keySet()){
            if(almacen_invitaciones.get(s).contains(new Invitacion_almacen("", gameId))){
                almacen_invitaciones.get(s).remove(new Invitacion_almacen("", gameId));
            }
        }
        game.startAlarma();
        if(game.getJugadores().size() == game.getNJugadores()){
            game.repartirManos();
            return game;
        } else throw new BeginGameException("Faltan jugadores.");
    }

    public Jugada playCard(String gameId, Jugador player, Carta card) {
        Optional<Partida> optionalGame;
        if(almacen_partidas.containsKey(gameId)){
            optionalGame = Optional.of(almacen_partidas.get(gameId));

            Partida game = optionalGame.get();
            Jugador p = game.getJugador(player);
            if(p == null) {
                throw new GameException("El juagdor no está en la partida");
            }
            if(!p.tieneCarta(card)) {
                throw new GameException("El jugador " + p.getNombre() + " no contiene la carta " + card);
            }
            game.cancelarAlarma();
            
            game.jugarCarta(card,p.getNombre());
            game.siguienteTurno();
            Jugada play = new Jugada(game.getUltimaCartaJugada(),game.getJugadores(), game.getTurno().getNombre());
            game.startAlarma();
            return play;
        } else { 
            optionalGame = null;
            throw new BeginGameException("Esa partida no existe");
        }   
    }

    public List<Carta> drawCards(String gameId, Jugador player, int nCards) {
        Optional<Partida> optionalGame;
        if(almacen_partidas.containsKey(gameId))
            optionalGame = Optional.of(almacen_partidas.get(gameId));
        else { 
            optionalGame = null;
            throw new BeginGameException("Esa partida no existe");
        }
        optionalGame.orElseThrow(() -> new BeginGameException("Game with provided id doesn't exist"));

        Partida game = optionalGame.get();
        List<Carta> cards_drawn = game.robarCartas(player.getNombre(), nCards);
        for(Carta c : cards_drawn) {
            player.addCarta(c);
        }
        return cards_drawn;
    }


    public String getPartidasUser(String user){
        for(Partida p : almacen_partidas.values()){
            if(p.playerAlreadyIn(new Jugador(user))){
                return p.getId();
            }
        }
        return "";
    }

    public void invitarAmigo(String username, String friendname, String gameId){
        List<Invitacion_almacen> lista;
        if(almacen_invitaciones.containsKey(friendname)){
            
            lista = almacen_invitaciones.get(friendname);
        } else {
            System.out.println(friendname + "no tiene invitaciones");
            lista = new ArrayList<Invitacion_almacen>();
        }
        lista.add(new Invitacion_almacen(username, gameId));
        System.out.println(almacen_invitaciones.size());
        almacen_invitaciones.put(friendname, lista);
        System.out.println(lista);
        System.out.println(almacen_invitaciones.size());
    }

    public void finJuego(String idPartida){
        for(String s : almacen_invitaciones.keySet()){
            if(almacen_invitaciones.get(s).contains(new Invitacion_almacen("", idPartida))){
                almacen_invitaciones.get(s).remove(new Invitacion_almacen("", idPartida));
            }
        }
        Partida p = almacen_partidas.get(idPartida);
        p.cancelarAlarma();
        if(almacen_partidas.containsKey(idPartida))
        almacen_partidas.remove(idPartida);
    }

    public List<Invitacion_almacen> getInvitacionesPartida(String username){
        if(almacen_invitaciones.containsKey(username)){
            System.out.println("Hay partidas de  " + username);
            return almacen_invitaciones.get(username);
        }
        else return new ArrayList<Invitacion_almacen>();
    }

    public boolean deleteGameInvitation(String username, String gameId){
        if(almacen_invitaciones.containsKey(username)){
            if(almacen_invitaciones.get(username).contains(new Invitacion_almacen("", gameId))){
                System.out.println("Contiene esa partida");
                almacen_invitaciones.get(username).remove(new Invitacion_almacen("", gameId));
                return true;
            } else{ 
                System.out.println("No contiene esa partida");
                return false;
            }
        } else {
            System.out.println("No aparece el usuario");
            return false;
        }
    }

}