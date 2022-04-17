package com.cerea_p1.spring.jpa.postgresql.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateGameRequest {
	@NotBlank
	private String player_name;

	public String getPlayerName() {
		return player_name;
	}

	public void setPlayerName(String player_name) {
		this.player_name = player_name;
	}
}