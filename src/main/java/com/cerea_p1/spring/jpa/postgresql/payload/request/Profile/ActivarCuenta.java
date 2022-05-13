package com.cerea_p1.spring.jpa.postgresql.payload.request.Profile;

import javax.validation.constraints.NotBlank;

public class ActivarCuenta {
    @NotBlank
    private String username;

    @NotBlank
    private String token;

    public String getUsername(){
        return username;
    }

    public String getToken(){
        return token;
    }
    
}
