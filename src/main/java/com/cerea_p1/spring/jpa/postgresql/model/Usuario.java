package com.cerea_p1.spring.jpa.postgresql.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;
import javax.persistence.CascadeType;

import com.cerea_p1.spring.jpa.postgresql.model.friends.Amigo;
import com.cerea_p1.spring.jpa.postgresql.model.friends.InvitacionAmistad;

@Entity
@Table(name = "usuario", 
            uniqueConstraints = { 
                @UniqueConstraint(columnNames = "nombre_de_usuario"),
                @UniqueConstraint(columnNames = "correo_electronico")
            })
public class Usuario {

    @Pattern(regexp = ".+[@].+[\\.].+")
    @Column(name = "correo_electronico", nullable = false, length = 255)
    private String email;

    @Id
    @Column(name = "nombre_de_usuario", nullable = false, length = 255)
    private String username;
    
    @NotNull
    @Column(name="contrasena", nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "usuario1", cascade=CascadeType.PERSIST)
    public List<Amigo> amigos;

    @OneToMany(mappedBy = "receptor", cascade=CascadeType.PERSIST)
    public List<InvitacionAmistad> invitaciones;

    @NotNull
    @Column(name="pais", nullable = false, length = 255)
    private String pais;

    @NotNull
    @Column(name="puntos", nullable = false)
    private int puntos;

    public Usuario(String username, String email, String password, String pais) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.pais = pais;
        this.puntos = 0;

        amigos = new ArrayList<Amigo>();;
    }

    public Usuario(){
        username = null;
        email = null;
        password = null;
        pais = null;
        puntos = 0;
        amigos = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
        
    public String getPais() {
        return pais;
    }
        
    public void setPais(String pais) {
        this.pais = pais;
    }
        
    public int getPuntos() {
        return puntos;
    }
        
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public List<Amigo> getAmigos(){
        return this.amigos;
    }

    public void setAmigo(Amigo amigo) {
       this.amigos.add(amigo);
    } 

    public void removeAmigo(Amigo amigo){
        this.amigos.remove(amigo);
    }
    
    @Override
    public String toString(){
        return "[name= " + this.username + ", email=" + this.email + ", pais=" + this.pais + ", puntos=" + this.puntos + "]";
    }
}