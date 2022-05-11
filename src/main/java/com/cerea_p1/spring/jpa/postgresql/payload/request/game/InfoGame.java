package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class InfoGame {
    @NotBlank
    private String idPartida;

    public String getIdPartida(){
        return idPartida;
    }
}
