package com.cerea_p1.spring.jpa.postgresql.payload.request.torneo;

import javax.validation.constraints.NotBlank;

public class CheckSemifinal {
    @NotBlank
    private String idPartida;

    @NotBlank
    private String idTorneo;


    public String getIdPartida(){
        return idPartida;
    }

    public String getIdTorneo(){
        return idTorneo;
    }
}