package com.cerea_p1.spring.jpa.postgresql.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "amigo", 
            uniqueConstraints = { 
                @UniqueConstraint(columnNames = {"usuario1","usuario2"})
            }
)
@IdClass(Amigo.class)
public class Amigo implements Serializable{
    @Id
    @Column(name = "usuario1", nullable = false, length = 255)
    private String usuario1;
    @Id
    @Column(name = "usuario2", nullable = false, length = 255)
    private String usuario2;
    

    public Amigo(String u, String u2) {
        usuario1 = u;
        usuario2 = u2;
    }

    public Amigo() {
    }

    public String getUsuario1(){
        return usuario1;
    }

    public void setUsuario1(String u){
        this.usuario1 = u;
    }

    public String getUsuario2(){
        return usuario2;
    }

    public void setUsuario2(String u){
        this.usuario2 = u;
    }

    @Override
    public String toString(){
        return "[username= " + this.usuario1 + "friendname= " + this.usuario2 + "]";
    }

    @Override
    public int hashCode() {
        return usuario1.hashCode() + usuario2.hashCode();
    }
}