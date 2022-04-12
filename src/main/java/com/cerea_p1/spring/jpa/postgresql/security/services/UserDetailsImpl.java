package com.cerea_p1.spring.jpa.postgresql.security.services;

import java.util.Collection;
//import java.util.List;
import java.util.Objects;
//import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<< HEAD
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;
=======
<<<<<<<< HEAD:prueba_2/pruebita/src/main/java/com/cerea_p1/spring/jpa/postgresql/security/services/UserDetailsImpl.java
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;
========
import com.cerea.spring.security.postgresql.models.Usuario;
import com.cerea.spring.security.postgresql.models.Amigo;
>>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2:Spring/onep1/src/main/java/com/cerea/spring/security/postgresql/security/services/UserDetailsImpl.java
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
    private String pais;
<<<<<<< HEAD
	private int puntos;
//	private List<Usuario> amigos;
	@JsonIgnore
	private String password;
	//private Collection<? extends GrantedAuthority> authorities;
	
//	public UserDetailsImpl(String username, String email, String password, String pais, int puntos, List<Usuario> amigos) {
	public UserDetailsImpl(String username, String email, String password, String pais, int puntos) {
=======
<<<<<<<< HEAD:prueba_2/pruebita/src/main/java/com/cerea_p1/spring/jpa/postgresql/security/services/UserDetailsImpl.java
	private int puntos;
	private List<Usuario> amigos;
	@JsonIgnore
	private String password;
	//private Collection<? extends GrantedAuthority> authorities;
	public UserDetailsImpl(String username, String email, String password, String pais, int puntos, List<Usuario> amigos) {
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
		this.username = username;
		this.email = email;
		this.password = password;
        this.pais = pais;
		this.puntos = puntos;
<<<<<<< HEAD
//		this.amigos = amigos;
=======
		this.amigos = amigos;
========
    private int puntos;
	private List<Amigo> amigos;
	
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String email, String password, String pais, int puntos, List<Amigo> amiguis, Collection<? extends GrantedAuthority> authorities) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.pais = pais;
        this.puntos = puntos;
		this.amigos = amiguis;
        this.authorities = authorities;
>>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2:Spring/onep1/src/main/java/com/cerea/spring/security/postgresql/security/services/UserDetailsImpl.java
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2

	}
	public static UserDetailsImpl build(Usuario user) {
		return new UserDetailsImpl(
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
<<<<<<< HEAD
                user.getPais(), user.getPuntos());//,user.getAmigos());

=======
                user.getPais(), user.getPuntos(),user.getAmigos());
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	public String getEmail() {
		return email;
	}
    public String getPais() {
		return pais;
	}
	public int getPuntos(){
		return puntos;
	}
<<<<<<< HEAD
=======
<<<<<<<< HEAD:prueba_2/pruebita/src/main/java/com/cerea_p1/spring/jpa/postgresql/security/services/UserDetailsImpl.java
========
    
	public List<Amigo> getAmigos() {
		return amigos;
	}

>>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2:Spring/onep1/src/main/java/com/cerea/spring/security/postgresql/security/services/UserDetailsImpl.java
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
<<<<<<< HEAD
	// public List<Usuario> getAmigos(){
	// 	return this.amigos;
	// }
=======

	public List<Usuario> getAmigos(){
		return this.amigos;
	}
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(email, user.email);
	}

/*	@Override
	public int hashCode(){
		return this.getUsername().hashCode();
	}*/
}