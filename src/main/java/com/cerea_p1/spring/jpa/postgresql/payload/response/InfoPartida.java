package com.cerea_p1.spring.jpa.postgresql.payload.response;

public class InfoPartida {
    private int numeroJugadores;
    private int tiempoTurno;

    public InfoPartida(int n, int t){
        this.numeroJugadores = n;
        this.tiempoTurno = t;
    }

    public int getNumeroJugadores(){
        return numeroJugadores;
    }

    public int getTiempoTurno(){
        return this.tiempoTurno;
    }
}
