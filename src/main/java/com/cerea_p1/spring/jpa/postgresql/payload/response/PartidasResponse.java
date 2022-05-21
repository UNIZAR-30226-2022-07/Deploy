package com.cerea_p1.spring.jpa.postgresql.payload.response;

public class PartidasResponse {
	private String partidas;

	public PartidasResponse(String partidas) {
	    this.partidas = partidas;
	  }

	public String getPartidas() {
		return partidas;
	}

	public void setPartidas(String partidas) {
		this.partidas = partidas;
	}
}
