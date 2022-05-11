package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import com.cerea_p1.spring.jpa.postgresql.model.game.Regla; 

public class CreateGameRequest {
	@NotBlank
	private String playername;

	@NotBlank
	private int nplayers;

	@NotBlank
	private int tturn;

	private ArrayList<Regla> rules = new ArrayList<Regla>();

	public String getPlayername() {
		return playername;
	}

	public int getNPlayers() {
		return nplayers;
	}

	public int getTTurn() {
		return tturn;
	}

	public ArrayList<Regla> getRules() {
		return rules;
	}
}