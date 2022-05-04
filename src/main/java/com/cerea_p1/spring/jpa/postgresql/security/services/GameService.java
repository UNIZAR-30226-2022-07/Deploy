package com.cerea_p1.spring.jpa.postgresql.security.services;

import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private ConcurrentHashMap<String,Partida> almacen_partidas;

    public GameService(){
        almacen_partidas = new ConcurrentHashMap<String,Partida>();
    }

    public Partida crearPartida(Jugador jugador,int nJugadores, int tTurno) {
        Partida game = new Partida(true);
        game.setId(UUID.randomUUID().toString());
        game.addJugador(jugador);
        game.setEstado(EstadoPartidaEnum.NEW);
        game.setNJugadores(nJugadores);
        game.setTTurno(tTurno);
        almacen_partidas.put(game.getId(),game);
        return game;
    }

    public List<Jugador> connectToGame(Jugador player, String gameId) {
        if(player != null){
            Optional<Partida> optionalGame;
            if(almacen_partidas.containsKey(gameId))
                optionalGame = Optional.of(almacen_partidas.get(gameId));
            else{ optionalGame = null; throw new ConnectGameException("Esa partida no existe"); 
            }

            optionalGame.orElseThrow(() -> new ConnectGameException("Game with provided id doesn't exist"));
            Partida game = optionalGame.get();

            if(game.getJugadores().size() >= game.getNJugadores()) throw new ConnectGameException("Partida llena.");
            if(!game.playerAlreadyIn(player))
                game.addJugador(player);
            return game.getJugadores();
        } else
            throw new ConnectGameException("Jugador no valido");
    }

    public String disconnectFromGame(Jugador player, String gameId){
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
        if(game.getJugadores().size() == game.getNJugadores()){
            game.repartirManos();
            return game;
        } else throw new BeginGameException("Faltan jugadores.");
    }

    /*public Game connectToRandomGame(Player player) {
        Optional<Game> optionalGame = gameRepository.findFirstByStatusAndSecondPlayerIsNull(GameStatusEnum.NEW);
        optionalGame.orElseThrow(() ->new GameException("There is no available Game!"));
        Game game = optionalGame.get();
        game.setSecondPlayer(player);
        game.setStatus(GameStatusEnum.IN_PROGRESS);
        gameRepository.save(game);
        return game;
    }

    public Game sow(Sow sow) {
        Optional<Game> optionalGame=gameRepository.findById(sow.getGameId());

        optionalGame.orElseThrow(() ->new GameException("Game with provided id doesn't exist"));
        Game game = optionalGame.get();

        Game gameAfterSow=sowService.sow(game,sow.getPitIndex());
        gameRepository.save(gameAfterSow);

        return gameAfterSow;
    } */
}