
package com.cerea_p1.spring.jpa.postgresql.payload.response;

import java.util.List;
import java.util.ArrayList;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String email;
    private String pais;
    private int puntos;
	private List<Usuario> amigos = new ArrayList<Usuario>();

	/* public JwtResponse(String accessToken, String username, String email, String pais, int puntos, List<Usuario> amigos)	{
        this.token = accessToken;
		this.username = username;
		this.email = email;
        this.pais = pais;     
        this.puntos = puntos;
		this.amigos = amigos;
    }  */	

	public JwtResponse(String accessToken, String username, String email, String pais, int puntos)	{
        this.token = accessToken;
		this.username = username;
		this.email = email;
        this.pais = pais;     
        this.puntos = puntos;
    } 	


    public String getAccessToken() {
		return token;
	}

	public String getTokenType() {
		return type;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

    public int getPuntos(){
        return this.puntos;
    }

    public String getPais(){
        return this.pais;
    }

	public List<Usuario> getAmigos(){
        return this.amigos;
    }

}