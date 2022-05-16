package com.cerea_p1.spring.jpa.postgresql.payload.response.torneo;

import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;
import com.cerea_p1.spring.jpa.postgresql.model.game.Regla;

import java.util.List;

public class InfoTorneoResponse {
    private String idTorneo;
    private int tiempoTurno;
    private List<Jugador> jugadores;
    private List<Regla> reglas;

    public InfoTorneoResponse(String id, int t, List<Jugador> j, List<Regla> r){
        this.idTorneo = id;
        this.tiempoTurno = t;
        this.jugadores = j;
        this.reglas = r;
    }
}
