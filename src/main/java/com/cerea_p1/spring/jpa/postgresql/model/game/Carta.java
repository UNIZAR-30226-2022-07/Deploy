package com.cerea_p1.spring.jpa.postgresql.model.game;

public class Carta {
    Numero numero;
    Color color;

    public Carta(Numero numero, Color color){
        this.numero = numero;
        this.color = color;
    }

    public Carta(String numero, String color){
        this.numero = Numero.valueOf(numero);
        this.color = Color.valueOf(color);
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

    @Override
    public String toString(){
        return numero + " " + color;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Carta){
            Carta c = (Carta)o;
            if(((c.getNumero() == Numero.MAS_CUATRO || c.getNumero() == Numero.CAMBIO_COLOR) && c.getNumero() == numero)|| (c.getColor() == this.color && c.getNumero() == this.numero)) return true;
            else return false;
        } else {
            return false;
        }
    }
    
}
