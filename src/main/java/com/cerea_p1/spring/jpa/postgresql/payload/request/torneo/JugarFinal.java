package com.cerea_p1.spring.jpa.postgresql.payload.request.torneo;

import javax.validation.constraints.NotBlank;

public class JugarFinal {
    @NotBlank
    private String username;

    @NotBlank
    private String torneoId;


    public String getUsername(){
        return username;
    }

    public String getTorneoId(){
        return torneoId;
    }
}
