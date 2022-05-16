package com.cerea_p1.spring.jpa.postgresql.payload.request.torneo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cerea_p1.spring.jpa.postgresql.model.game.Regla;

public class CrearTorneo {
    @NotBlank
    private String username;

    @NotBlank
    private int tiempoTurno;

    @NotBlank
    private List<Regla> reglas;

    public String getUsername(){
        return username;
    }

    public int getTiempoTurno(){
        return tiempoTurno;
    }

    public List<Regla> getReglas(){
        return reglas;
    }
}
