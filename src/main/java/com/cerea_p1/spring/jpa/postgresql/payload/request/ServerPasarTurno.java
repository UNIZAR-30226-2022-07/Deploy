package com.cerea_p1.spring.jpa.postgresql.payload.request;

import javax.validation.constraints.NotBlank;

public class ServerPasarTurno {
    @NotBlank
    private String idPartida;

    @NotBlank
    private String turno;

    public ServerPasarTurno(String i, String t){
        this.idPartida = i;
        this.turno = t;
    }

    public String getIdPartida(){
        return idPartida;
    }

    public String getTurno(){
        return turno;
    }
}
