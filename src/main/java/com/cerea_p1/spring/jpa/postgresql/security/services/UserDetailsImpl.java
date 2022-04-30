package com.cerea_p1.spring.jpa.postgresql.security.services;

import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
    private String pais;
	private int puntos;

	@JsonIgnore
	private String password;
	
	public UserDetailsImpl(String username, String email, String password, String pais, int puntos) {

		this.username = username;
		this.email = email;
		this.password = password;
        this.pais = pais;
		this.puntos = puntos;
	}
    	
	public static UserDetailsImpl build(Usuario user) {
		return new UserDetailsImpl(
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
                user.getPais(), user.getPuntos());
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

	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}

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

}