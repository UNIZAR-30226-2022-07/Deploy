package com.cerea_p1.spring.jpa.postgresql.payload.request.friends;

import javax.validation.constraints.NotBlank;

public class AddFriendRequest {

    @NotBlank
	private String username;

	@NotBlank
	private String friendname;

    public String getUsername() {
		return username;
	}

    public String getFriendname(){
        return friendname;
    }

    public void setFriendname(String friendname){
        this.friendname = friendname;
    }
}