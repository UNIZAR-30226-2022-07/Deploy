package com.cerea_p1.spring.jpa.postgresql.model.game;

public class Carta {
    Numero numero;
    Color color;

    public Carta(Numero numero, Color color){
        this.numero = numero;
        this.color = color;
    }

    public void setNumero(Numero numero){
        this.numero = numero;
    }

    public Numero getNumero(){
        return numero;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
    
}
