package com.cerea_p1.spring.jpa.postgresql.security.services;

import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.response.Jugada;
import com.cerea_p1.spring.jpa.postgresql.exception.*;
import lombok.AllArgsConstructor;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.security.KeyStore.Entry;
import java.util.List;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class TorneoService {

    private ConcurrentHashMap<String,Torneo> almacen_torneos;

    @Autowired
    private GameService gameService;

    public TorneoService(){
        almacen_torneos = new ConcurrentHashMap<String,Torneo>();
    }

    public Torneo crearTorneo(Jugador jugador, int tTurn, List<Regla> reglas) {
        List<Partida> lista_partidas = new ArrayList<Partida>();
        int nJugadores = 3;

        //partidas que forman el torneo. semifinal a 3, final a 3.
        lista_partidas.add(new Partida(nJugadores,tTurn, reglas));
        lista_partidas.add(new Partida(nJugadores,tTurn, reglas));
        lista_partidas.add(new Partida(nJugadores,tTurn, reglas));
        lista_partidas.add(new Partida(nJugadores,tTurn, reglas));
        for(Partida p : lista_partidas){
            gameService.addPartida(p);
        }
        Torneo tor = new Torneo(lista_partidas);
        tor.addJugador(jugador);
        almacen_torneos.put(tor.getIdTorneo(),tor);
        return tor;
    }

    public List<Jugador> connectToTorneo(Jugador player, String torneoId) {
        if(player != null){
            Optional<Torneo> optionalTorneo;
            if(almacen_torneos.containsKey(torneoId))
                optionalTorneo = Optional.of(almacen_torneos.get(torneoId));
            else { 
                optionalTorneo = null; 
                throw new ConnectGameException("Ese torneo no existe"); 
            }

            optionalTorneo.orElseThrow(() -> new ConnectGameException("Tournament with provided id doesn't exist"));

            Torneo torneo = optionalTorneo.get();
            if(torneo.getJugadores().size() >= torneo.getNJugadores()) 
                throw new ConnectGameException("Torneo lleno.");
            if(!torneo.playerAlreadyIn(player))
                torneo.addJugador(player);
            return torneo.getJugadores();
        } else
            throw new ConnectGameException("Jugador no valido");
    }

    public Torneo beginTorneo(String torneoId){
        Optional<Torneo> optionalTorneo;
        if(almacen_torneos.containsKey(torneoId))
            optionalTorneo = Optional.of(almacen_torneos.get(torneoId));
        else { 
            optionalTorneo = null;
            throw new BeginGameException("Ese torneo no existe");
        }

        optionalTorneo.orElseThrow(() -> new BeginGameException("Tournament with provided id doesn't exist"));

        Torneo torneo = optionalTorneo.get();

        if(torneo.getJugadores().size() == torneo.getNJugadores()){
            return torneo;
        } else throw new BeginGameException("Faltan jugadores.");
    }    

    public List<String> listaTorneos(){
        List<String> lista = new ArrayList<String>();
        for(String s : almacen_torneos.keySet()){
            if(almacen_torneos.get(s).getEstadoTorneo() == EstadoPartidaEnum.NEW){
                lista.add(s);
            }
        }
        return lista;
    }

    public String disconnectTorneo(String torneoId, String username){
        if(username != null){
            Optional<Torneo> optionalTorneo;
            if(almacen_torneos.containsKey(torneoId))
                optionalTorneo = Optional.of(almacen_torneos.get(torneoId));
            else { 
                optionalTorneo = null; 
                throw new ConnectGameException("Ese torneo no existe"); 
            }

            optionalTorneo.orElseThrow(() -> new ConnectGameException("Tournament with provided id doesn't exist"));

            Torneo torneo = optionalTorneo.get();
            if(torneo.getEstadoTorneo() != EstadoPartidaEnum.NEW) throw new DisconnectGameException("No puedes salir de la partida.");
            if(torneo.playerAlreadyIn(new Jugador(username))){
                torneo.removePlayer(new Jugador(username));
                if(torneo.getJugadores().size() == 0)
                    almacen_torneos.remove(torneoId);

                return username +" disconnected successfully from "+ torneoId;
            }
            else
                throw new DisconnectGameException("Jugador no pertenece a la partida");
        } else
            throw new ConnectGameException("Jugador no valido");
    }

    public String jugarFinal(String username, String torneoId){
        Torneo t = almacen_torneos.get(torneoId);
        Partida p = t.getPartidas().get(3);
        return p.getId();
    }
}