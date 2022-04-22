package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class CreateGameRequest {
	@NotBlank
	private String playerName;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String p){
		playerName = p;
	}
}