package com.cerea_p1.spring.jpa.postgresql.payload.request;

import javax.validation.constraints.NotBlank;

public class AddFriendRequest {

    @NotBlank
	private String username;

	@NotBlank
	private String friendname;

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public String getFriendname(){
        return friendname;
    }

    public void setFriendname(String friendname){
        this.friendname = friendname;
    }
}