package com.cerea_p1.spring.jpa.postgresql.payload.request.Profile;

import javax.validation.constraints.NotBlank;

public class CambiarUsernameRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String newUsername;

    public String getUsername() {
		return username;
	}
    

    public String getNewUsername(){
        return newUsername;
    }
}
