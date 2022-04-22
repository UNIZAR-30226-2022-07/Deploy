package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class DisconnectRequest {
    @NotBlank
    private String playerName;
    @NotBlank
    private String gameId;

    public String getPlayerName() {
		return playerName;
	}

    public String getGameId() {
        return gameId;
    }
}