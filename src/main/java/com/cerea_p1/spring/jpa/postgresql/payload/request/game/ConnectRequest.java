package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class ConnectRequest {
    @NotBlank
    private String player_name;
    @NotBlank
    private String gameId;

    public String getPlayerName() {
		return player_name;
	}

    public String getGameId() {
        return gameId;
    }
}
