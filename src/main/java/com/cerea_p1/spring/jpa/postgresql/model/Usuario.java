package com.cerea_p1.spring.jpa.postgresql.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
import javax.persistence.CascadeType;

@Entity
@Table(name = "usuario", 
            uniqueConstraints = { 
                @UniqueConstraint(columnNames = "nombre_de_usuario"),
                @UniqueConstraint(columnNames = "correo_electronico")
            })
public class Usuario {

    @Id
    @Pattern(regexp = ".+[@].+[\\.].+")
    @Column(name = "correo_electronico", nullable = false, length = 255)
    private String email;

    
    @Column(name = "nombre_de_usuario", nullable = false, length = 255, unique = true)
    @Size(min = 4)
    private String username;
    
    @NotNull
    @Column(name="contrasena", nullable = false, length = 255)
    private String password;

    @JoinTable(name = "invitacion", joinColumns = {
        @JoinColumn(name = "receptor", referencedColumnName = "correo_electronico", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "emisor", referencedColumnName = "correo_electronico", nullable = false)})
        @ManyToMany(cascade = {CascadeType.PERSIST})
    // @PreRemove
    public List<Usuario> invitacionesRecibidas;

    @ManyToMany(mappedBy = "invitacionesRecibidas")
    public List<Usuario> invitacionesEnviadas;

    @JoinTable(name = "amigo", joinColumns = {
        @JoinColumn(name = "usuario2", referencedColumnName = "correo_electronico", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "usuario1", referencedColumnName = "correo_electronico", nullable = false)})
        @ManyToMany(cascade = {CascadeType.PERSIST})
    public List<Usuario> amigos;

    @ManyToMany(mappedBy = "amigos")
    public List<Usuario> amigosInv;


    @NotNull
    @Column(name="pais", nullable = false, length = 255)
    private String pais;

    @NotNull
    @Column(name="puntos", nullable = false)
    private int puntos;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    public Usuario(String username, String email, String password, String pais) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.pais = pais;
        this.puntos = 0;
        resetPasswordToken = "";

        amigos = new ArrayList<Usuario>();
        amigosInv = new ArrayList<Usuario>();
        invitacionesRecibidas = new ArrayList<Usuario>();
        invitacionesEnviadas = new ArrayList<Usuario>();
    }

    public Usuario(){
        username = null;
        email = null;
        password = null;
        pais = null;
        puntos = 0;
        amigos = null;
        resetPasswordToken = "";

        amigosInv = null;
        invitacionesRecibidas = null;
        invitacionesEnviadas = null;
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

    public List<Usuario> getAmigos(){
        return this.amigos;
    }

    public void setAmigos(List<Usuario> amig) {
       amigos = amig;
    }
    
    public void addAmigo(Usuario amigo){
        this.amigos.add(amigo);
    }

    public void removeAmigo(Usuario amigo){
        this.amigos.remove(amigo);
    }

    public List<Usuario> getAmigosInv(){
        return this.amigosInv;
    }

    public void setAmigosInv(List<Usuario> amig) {
       amigosInv = amig;
    }
    
    public void addAmigoInv(Usuario amigo){
        this.amigosInv.add(amigo);
    }

    public void removeAmigoInv(Usuario amigo){
        this.amigosInv.remove(amigo);
    }

    public List<Usuario> getInvitacionesRecibidas(){
        return this.invitacionesRecibidas;
    }

    public void setInvitacion(List<Usuario> inv){
        invitacionesRecibidas = inv;
    }

    public void addInvitacion(Usuario inv){
        this.invitacionesRecibidas.add(inv);
    }

    public void removeInvitacion(Usuario inv){
        this.invitacionesRecibidas.remove(inv);
    }

    public List<Usuario> getInvitacionesEnviadas(){
        return this.invitacionesEnviadas;
    }

    public void setInvitacionesEnviadas(List<Usuario> inv){
        invitacionesEnviadas = inv;

    }
    public void addInvitacionesEnviadas(Usuario inv){
        this.invitacionesEnviadas.add(inv);
    }

    public void removeInvitacionesEnviadas(Usuario inv){
        this.invitacionesEnviadas.remove(inv);
    }

    public String getResetPasswordToken(){
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String token){
        resetPasswordToken = token;
    }

    @PreRemove
    public void removeAmigos(){
        for(Usuario u : amigos){
            u.removeAmigo(this);
        }
        for(Usuario u : invitacionesEnviadas){
            u.removeInvitacion(this);
        }
    }
    
    @Override
    public String toString(){
        return "[name= " + this.username + ", email=" + this.email + ", pais=" + this.pais + ", puntos=" + this.puntos + "]";
    }
}