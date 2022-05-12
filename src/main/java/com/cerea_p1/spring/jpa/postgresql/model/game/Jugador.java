package com.cerea_p1.spring.jpa.postgresql.model.game;

import java.util.*;

public class Jugador {
    private String nombre;
    private List<Carta> mano;

    public Jugador(String name){
        nombre = name;
        mano = new ArrayList<Carta>();
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String name){
        nombre = name;
    }

    public void addCarta(Carta c){
        mano.add(c);
    }

    public List<Carta> getCartas(){
        return this.mano;
    }

    public boolean tieneCarta(Carta c) {
        return mano.contains(c);
    }

    public boolean deleteCarta(Carta c){
        if (mano.contains(c)){
            mano.remove(c);
            return true;
        }
        return false;
    }

    public boolean equals(Object o){
        if (!(o instanceof Jugador)){ 
            return false;
        }
        Jugador j = (Jugador)o;
        return Objects.equals(j.getNombre(), this.nombre);
    }
}
