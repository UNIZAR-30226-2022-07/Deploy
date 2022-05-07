package com.cerea_p1.spring.jpa.postgresql.payload.request.Profile;

import javax.validation.constraints.NotBlank;

public class OlvidoContrasena {
    @NotBlank
    private String email;

    public String getEmail() {
		return email;
	}
}
