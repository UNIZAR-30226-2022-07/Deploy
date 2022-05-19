package com.cerea_p1.spring.jpa.postgresql.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cerea_p1.spring.jpa.postgresql.model.game.Carta;
import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;


public class ServerPasarTurno {
    @NotBlank
    private String idPartida;

    // @NotBlank
    // private Carta ultimaCarta;
    
    // @NotBlank
    // private List<Jugador> jugadores;

    // @NotBlank
    // private String turno;

    // public ServerPasarTurno(String i, Carta c, List<Jugador> j, String t){
    //     this.idPartida = i;
    //     // this.ultimaCarta = c;
    //     // this.jugadores = j;
    //     // this.turno = t;
    // }
    public ServerPasarTurno(String i){
        this.idPartida = i;
        // this.ultimaCarta = c;
        // this.jugadores = j;
        // this.turno = t;
    }

    public String getIdPartida(){
        return idPartida;
    }

    // public Carta getUltimaCarta(){
    //     return ultimaCarta;
    // }

    // public List<Jugador> getJugadores(){
    //     return jugadores;
    // }

    // public String getTurno(){
    //     return turno;
    // }
}
