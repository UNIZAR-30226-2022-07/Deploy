package com.cerea_p1.spring.jpa.postgresql.payload.response;

import com.cerea_p1.spring.jpa.postgresql.model.game.Carta;
import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;

import java.util.ArrayList;
import java.util.List;

public class Jugada {
    private Carta carta;
    private ArrayList<InfoJugador> jugadores;    
    
    public Jugada(Carta c, List<Jugador> j){
        carta = c;
        for(Jugador h : j){
            jugadores.add(new InfoJugador(h.getNombre(), h.getCartas().size()));
        }
    }

    public Carta getCarta(){
        return carta;
    }

    public ArrayList<InfoJugador> getJugadores(){
        return jugadores;
    }
}
