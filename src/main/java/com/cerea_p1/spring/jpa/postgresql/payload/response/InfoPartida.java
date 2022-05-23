package com.cerea_p1.spring.jpa.postgresql.payload.response;

import java.util.List;

import com.cerea_p1.spring.jpa.postgresql.model.game.EstadoPartidaEnum;
import com.cerea_p1.spring.jpa.postgresql.model.game.Regla;

public class InfoPartida {
    private int numeroJugadores;
    private int tiempoTurno;
    private List<String> jugadores;
    private List<Regla> reglas;
    private EstadoPartidaEnum estado;

    public InfoPartida(int n, int t, List<String> jugadores, List<Regla> reglas, EstadoPartidaEnum estado){
        this.numeroJugadores = n;
        this.tiempoTurno = t;
        this.jugadores = jugadores;
        this.reglas = reglas;
        this.estado = estado;
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

    public List<Regla> getReglas(){
        return reglas;
    }

    public EstadoPartidaEnum getEstado(){
        return estado;
    }
}