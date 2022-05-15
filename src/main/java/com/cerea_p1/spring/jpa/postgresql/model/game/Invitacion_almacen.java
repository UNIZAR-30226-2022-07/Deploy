package com.cerea_p1.spring.jpa.postgresql.model.game;

import javax.validation.constraints.NotBlank;

public class Invitacion_almacen {
    @NotBlank
    private String invitador;
    @NotBlank
    private String game;

    public Invitacion_almacen(String invitador, String game) {
        this.invitador = invitador;
        this.game = game;
    }

    public String getFriendname(){
        return invitador;
    }

    public String getGame(){
        return game;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Invitacion_almacen){
            Invitacion_almacen i = (Invitacion_almacen)o;
            return game.equals(i.getGame());
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return invitador + " : " + game;
    }
}