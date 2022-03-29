package com.cerea_p1.spring.jpa.postgresql.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

@Entity
@Table(name = "amigo", 
            uniqueConstraints = { 
                @UniqueConstraint(columnNames = {"usuario1","usuario2"})
            }
)
public class Amigo {
    @Id
    @Column(name = "usuario1", nullable = false, length = 255)
    private Usuario usuario1;
    @Id
    @Column(name = "usuario2", nullable = false, length = 255)
    private Usuario usuario2;
    

    public Amigo(Usuario u, Usuario u2) {
        usuario1 = u;
        usuario2 = u2;
    }

    public Amigo() {
    }

    public Usuario getUsuario1(){
            return usuario1;
        }

    public void setUsuario1(Usuario u){
        this.usuario1 = u;
    }

    public Usuario getUsuario2(){
        return usuario2;
    }

    public void setUsuario2(Usuario u){
        this.usuario2 = u;
    }

    @Override
    public String toString(){
        return "[username= " + this.usuario1.getUsername() + "friendname= " + this.usuario2.getUsername() + "]";
    }
}