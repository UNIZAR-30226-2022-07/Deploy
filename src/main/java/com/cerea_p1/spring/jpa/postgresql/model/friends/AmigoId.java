package com.cerea_p1.spring.jpa.postgresql.model.friends;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

public class AmigoId implements Serializable{

    private Usuario usuario1;

    private Usuario usuario2;

    public AmigoId(Usuario u1, Usuario u2){
        usuario1 = u1;
        usuario2 = u2;
    }

    public AmigoId(){
        usuario1 = null;
        usuario2 = null;
    }
    
}