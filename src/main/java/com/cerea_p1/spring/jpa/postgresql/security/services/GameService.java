package com.cerea_p1.spring.jpa.postgresql.security.services;

import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: e.shakeri
 */

@Service
@AllArgsConstructor
public class GameService {

    private ConcurrentHashMap<String,Partida> almacen_partidas = new ConcurrentHashMap<String,Partida>();

    //private final SowService sowService;

    public Partida crearPartida(Jugador jugador) {
        Partida game = new Partida(true);
        game.setId(UUID.randomUUID().toString());
        game.addJugador(jugador);;
        game.setEstado(EstadoPartidaEnum.NEW);
        almacen_partidas.put(game.getId(),game);
        return game;
    }

    public Partida connectToGame(Jugador player, String gameId) {
        //if(!almacen_partidas.contains(gameId))
        Optional<Partida> optionalGame = Optional.of(almacen_partidas.get(gameId));

        optionalGame.orElseThrow(() -> new GameException("Game with provided id doesn't exist"));
        Partida game = optionalGame.get();

        game.addJugador(player);

        game.setEstado(EstadoPartidaEnum.IN_PROGRESS);
        almacen_partidas.put(gameId,game);
        return game;
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