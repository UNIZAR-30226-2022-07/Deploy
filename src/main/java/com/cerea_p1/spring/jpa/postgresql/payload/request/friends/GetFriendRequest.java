package com.cerea_p1.spring.jpa.postgresql.payload.request.friends;

import javax.validation.constraints.NotBlank;

public class GetFriendRequest {

    @NotBlank
	private String username;

    public String getUsername() {
		return username;
	}
}