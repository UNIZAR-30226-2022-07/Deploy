package com.cerea_p1.spring.jpa.postgresql.model.game;

import com.cerea_p1.spring.jpa.postgresql.model.game.Numero;
import com.cerea_p1.spring.jpa.postgresql.model.game.Color;

public class Carta {
    Numero num;
    Color col;

    public Carta(Numero numero, Color color){
        num = numero;
        col = color;
    }

    public void setNumero(Numero numero){
        num = numero;
    }

    public Numero getNumero(){
        return num;
    }

    public void setColor(Color color){
        col = color;
    }

    public Color getColor(){
        return col;
    }
    
}
