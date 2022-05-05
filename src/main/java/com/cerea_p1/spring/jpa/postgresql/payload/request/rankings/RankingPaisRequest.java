package com.cerea_p1.spring.jpa.postgresql.payload.request.rankings;

import javax.validation.constraints.NotBlank;

public class RankingPaisRequest {
    @NotBlank
    private String pais;

    public String getPais() {
		return pais;
	}
}