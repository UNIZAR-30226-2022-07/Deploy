package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

import com.cerea_p1.spring.jpa.postgresql.model.game.Carta;

public class JugarCarta {
    @NotBlank
    private String username;

    @NotBlank
    private Carta carta;

    public String getUsername(){
        return username;
    }

    public Carta getCarta(){
        return carta;
    }
}