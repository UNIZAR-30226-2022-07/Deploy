package com.cerea_p1.spring.jpa.postgresql.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}