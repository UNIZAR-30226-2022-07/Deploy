package com.cerea_p1.spring.jpa.postgresql.model.game;

import javax.validation.constraints.NotBlank;

public class Invitacion {
    @NotBlank
    private String username;
    @NotBlank
    private String friendname;
    @NotBlank
    private String gameId;

    public Invitacion(String username, String friendname, String game) {
        this.username = username;
        this.friendname = friendname;
        this.gameId = game;
    }

    public String getUsername(){
        return username;
    }

    public String getFriendname(){
        return friendname;
    }

    public String getGameId(){
        return gameId;
    }
}