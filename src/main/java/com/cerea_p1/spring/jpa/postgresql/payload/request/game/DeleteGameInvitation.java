package com.cerea_p1.spring.jpa.postgresql.payload.request.game;

import javax.validation.constraints.NotBlank;

public class DeleteGameInvitation {
    @NotBlank
    private String username;

    @NotBlank
    private String gameId;

    public String getUsername(){
        return username;
    }

    public String getGameId(){
        return gameId;
    }
}
