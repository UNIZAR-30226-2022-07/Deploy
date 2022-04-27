package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class CreateGameRequest {
	@NotBlank
	private String playerName;

	@NotBlank
	private int nPlayers;

	@NotBlank
	private int tTurn;

	public String getPlayerName() {
		return playerName;
	}

	public int getNPlayers() {
		return nPlayers;
	}

	public int getTTurn() {
		return tTurn;
	}
}