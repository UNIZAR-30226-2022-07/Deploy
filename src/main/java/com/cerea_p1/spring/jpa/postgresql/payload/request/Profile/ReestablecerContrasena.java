package com.cerea_p1.spring.jpa.postgresql.payload.request.Profile;

import javax.validation.constraints.NotBlank;

public class ReestablecerContrasena {
    @NotBlank
    private String email;

    @NotBlank
    private String token;

    @NotBlank
    private String password;

    public String getEmail() {
		return email;
	}

    public String getToken(){
        return token;
    }

    public String getPassword(){
        return password;
    }
}
