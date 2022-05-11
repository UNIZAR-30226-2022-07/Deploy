package com.cerea_p1.spring.jpa.postgresql.payload.response;

import java.util.List;

public class InfoPartida {
    private int numeroJugadores;
    private int tiempoTurno;
    private List<String> jugadores;

    public InfoPartida(int n, int t, List<String> jugadores){
        this.numeroJugadores = n;
        this.tiempoTurno = t;
        this.jugadores = jugadores;
    }

    public int getNumeroJugadores(){
        return numeroJugadores;
    }

    public int getTiempoTurno(){
        return this.tiempoTurno;
    }

    public List<String> getJugadores(){
        return jugadores;
    }
}
