package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class GetMano {
    @NotBlank
    private String username;

    @NotBlank
    private String idPartida;

    public String getUsername(){
        return username;
    }

    public String getIdPartida() {
        return idPartida;
    }
}