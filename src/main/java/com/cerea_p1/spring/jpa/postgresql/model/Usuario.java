package com.cerea_p1.spring.jpa.postgresql.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "usuario", 
            uniqueConstraints = { 
                @UniqueConstraint(columnNames = "nombre_de_usuario"),
                @UniqueConstraint(columnNames = "correo_electronico")
            })
public class Usuario {

    @Id
    @Pattern(regexp = ".+[@].+[\\.].+")
    @Column(name = "correo_electronico", nullable = false, length = 60)
    private String email;

    @Column(name = "nombre_de_usuario", nullable = false, length = 50)
    private String username;
    
    @NotNull
    @Column(name="contrasena", nullable = false, length = 30)
    private String password;
    
    // @NotNull
    // @OneToMany(mappedBy = "usuario")
    // private List<Amigo> amigos = new ArrayList<Amigo>();

    @NotNull
    @Column(name="pais", nullable = false, length = 70)
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
    
    @Override
    public String toString(){
        return "[name= " + this.username + ", email=" + this.email + ", pais=" + this.pais + ", puntos=" + this.puntos + "]";
    }
}