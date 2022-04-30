package com.cerea_p1.spring.jpa.postgresql.model.friends;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

@Entity
@Table(name = "invitacion"//, 
    //        uniqueConstraints = { 
    //            @UniqueConstraint(columnNames = {"emisor","receptor"})
    //        }
)
@IdClass(InvitacionAmistadId.class)
public class InvitacionAmistad implements Serializable {
    @Id
  //  @Column(name = "emisor", nullable = false)
    @ManyToOne(optional=false)
    @JoinColumn(name="emisor", referencedColumnName="nombre_de_usuario")

    private Usuario emisor;
    @Id
    // @Column(name = "receptor", nullable = false)
    @ManyToOne(optional=false)
    @JoinColumn(name="receptor", referencedColumnName="nombre_de_usuario")
    private Usuario receptor;

  //  private AmigoId clave;
    

    public InvitacionAmistad(Usuario u, Usuario u2) {
        emisor = u;
        receptor = u2;
    }

    public InvitacionAmistad() {
        emisor = null;
        receptor = null;
    }

    public Usuario getEmisor(){
        return emisor;
    }

    public void setEmisor(Usuario u){
        this.emisor = u;
    }

    public Usuario getReceptor(){
        return receptor;
    }

    public void setReceptor(Usuario u){
        this.receptor = u;
    }

    @Override
    public String toString(){
        return "[username= " + this.emisor + "friendname= " + this.receptor + "]";
    }
}


