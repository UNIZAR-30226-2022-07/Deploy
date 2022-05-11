package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

public class CreateGameRequest {
	@NotBlank
	private String playername;

	@NotBlank
	private int nplayers;

	@NotBlank
	private int tturn;

	private int nrules;

	//private ArrayList<Reglas> rules = ArrayList<Reglas>();

	public String getPlayername() {
		return playername;
	}

	public int getNPlayers() {
		return nplayers;
	}

	public int getTTurn() {
		return tturn;
	}
}