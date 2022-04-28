package com.cerea_p1.spring.jpa.postgresql.model.friends;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

public class InvitacionAmistadId implements Serializable{

    private Usuario emisor;

    private Usuario receptor;

    public InvitacionAmistadId(Usuario emisor, Usuario receptor){
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public InvitacionAmistadId(){
        emisor = null;
        receptor = null;
    }

    public Usuario getEmisor(){
        return emisor;
    }

    public void setEmisor(Usuario u){
        emisor = u;
    }

    public Usuario getReceptor(){
        return receptor;
    }

    public void setReceptor(Usuario u){
        receptor = u;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        else if(o instanceof InvitacionAmistadId){
            InvitacionAmistadId a = (InvitacionAmistadId)o;
            if(this.getEmisor().getUsername().equals(a.getEmisor().getUsername()) && this.getReceptor().getUsername().equals(a.getReceptor().getUsername())) return  true;
            else return false;
        } else return false;
    }

    @Override
    public int hashCode(){
        return this.getEmisor().hashCode() + this.getReceptor().hashCode();
    }
}