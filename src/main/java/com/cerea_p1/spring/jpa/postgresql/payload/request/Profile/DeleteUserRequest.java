package com.cerea_p1.spring.jpa.postgresql.payload.request.Profile;
import javax.validation.constraints.NotBlank;
public class DeleteUserRequest {
    @NotBlank
    private String username;

    public String getUsername() {
		return username;
	}
}