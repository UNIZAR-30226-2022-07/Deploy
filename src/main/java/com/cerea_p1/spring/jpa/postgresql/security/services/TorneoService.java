package com.cerea_p1.spring.jpa.postgresql.security.services;

import com.cerea_p1.spring.jpa.postgresql.model.game.*;
import com.cerea_p1.spring.jpa.postgresql.payload.response.Jugada;
import com.cerea_p1.spring.jpa.postgresql.exception.*;
import lombok.AllArgsConstructor;

import org.hibernate.mapping.Set;
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
        
        Torneo tor = new Torneo(lista_partidas);
        tor.addJugador(jugador);
        almacen_torneos.put(tor.getIdTorneo(),tor);
        return tor;
    }

    // public List<Jugador> connectToTorneo(Jugador player, String torneoId) {
    //     if(player != null){
    //         Optional<Torneo> optionalTorneo;
    //         if(almacen_torneos.containsKey(torneoId))
    //             optionalTorneo = Optional.of(almacen_torneos.get(torneoId));
    //         else { 
    //             optionalTorneo = null; 
    //             throw new ConnectGameException("Ese torneo no existe"); 
    //         }

    //         optionalTorneo.orElseThrow(() -> new ConnectGameException("Tournament with provided id doesn't exist"));

    //         Torneo torneo = optionalTorneo.get();
    //         if(torneo.getJugadores().size() >= torneo.getNJugadores()) 
    //             throw new ConnectGameException("Torneo lleno.");
    //         if(!torneo.playerAlreadyIn(player))
    //             torneo.addJugador(player);
    //         return torneo.getJugadores();
    //     } else
    //         throw new ConnectGameException("Jugador no valido");
    // }

    public List<String> listaTorneos(){
        List<String> lista = new ArrayList<String>();
        for(String s : almacen_torneos.keySet()){
            if(almacen_torneos.get(s).getEstadoTorneo() == EstadoPartidaEnum.NEW){
                lista.add(s);
            }
        }
        return lista;
    }
}