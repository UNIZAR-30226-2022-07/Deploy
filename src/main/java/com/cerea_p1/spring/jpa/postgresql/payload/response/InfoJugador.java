package com.cerea_p1.spring.jpa.postgresql.payload.response;

public class InfoJugador {
    private String username;
    private int numeroCartas;

    public InfoJugador(String u, int n){
        username = u;
        numeroCartas = n;
    }

    public String getUsername(){
        return username;
    }

    public int getNumeroCartas(){
        return numeroCartas;
    }
}
