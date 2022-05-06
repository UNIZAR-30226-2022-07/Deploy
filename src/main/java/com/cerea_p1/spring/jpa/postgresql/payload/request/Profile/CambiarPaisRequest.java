package com.cerea_p1.spring.jpa.postgresql.payload.request.Profile;
import javax.validation.constraints.NotBlank;
public class CambiarPaisRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String pais;

    public String getUsername() {
		return username;
	}

    public String getPais(){
        return pais;
    }
}