package com.cerea_p1.spring.jpa.postgresql.utils;

import com.google.gson.Gson;

public class Sender {
	public static <T> T recibir(String jsonData, Class<T> expectedClass){
        return new Gson().fromJson(jsonData, expectedClass);
    }
	
	public static String enviar(Object objeto){
        return new Gson().toJson(objeto);
    }
}