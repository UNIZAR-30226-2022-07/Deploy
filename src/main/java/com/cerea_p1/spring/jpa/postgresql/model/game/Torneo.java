package com.cerea_p1.spring.jpa.postgresql.model.game;

import java.util.ArrayList;
import java.util.List;


public class Torneo{
    private ArrayList<Partida> torneo = new ArrayList<Partida>();
    private List<Jugador> jugadores = new ArrayList<Jugador>();

    public Torneo (ArrayList<Partida> torneo){
        this.torneo = torneo;
    }

    public void addJugador(Jugador j) {
        jugadores.add(j);
    }
}