package com.cerea_p1.spring.jpa.postgresql.model.friends;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

public class AmigoId implements Serializable{

    private Usuario usuario1;

    private Usuario usuario2;

    public AmigoId(Usuario u1, Usuario u2){
        usuario1 = u1;
        usuario2 = u2;
    }

    public AmigoId(){
        usuario1 = null;
        usuario2 = null;
    }

    public void setUsuario1(Usuario u){
        usuario1 = u;
    }

    public Usuario getUsuario1(){
        return usuario1;
    }

    public void setUsuario2(Usuario u){
        usuario2 = u;
    }

    public Usuario getUsuario2(){
        return usuario2;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        else if(o instanceof AmigoId){
            AmigoId a = (AmigoId)o;
            if(this.getUsuario1().getUsername().equals(a.getUsuario1().getUsername()) && this.getUsuario2().getUsername().equals(a.getUsuario2().getUsername())) return  true;
            else return false;
        } else return false;
    }

    @Override
    public int hashCode(){
        return this.getUsuario1().hashCode() + this.getUsuario2().hashCode();
    }
    
}