package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class GetPartidas {
    @NotBlank
    private String username;

    public String getUsername(){
        return username;
    }
}
