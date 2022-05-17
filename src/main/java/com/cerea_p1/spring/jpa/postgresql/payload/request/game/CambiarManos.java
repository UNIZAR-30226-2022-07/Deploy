package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class CambiarManos {
    @NotBlank
    private String gameId;

    @NotBlank
    private String player1;

    @NotBlank
    private String player2;

    public String getGameId(){
        return gameId;
    }

    public String getPlayer1(){
        return player1;
    }

    public String getPlayer2(){
        return player2;
    }
}
