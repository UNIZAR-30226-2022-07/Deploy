package com.cerea_p1.spring.jpa.postgresql.model.friends;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

public class InvitacionAmistadId implements Serializable{

    private Usuario emisor;

    private Usuario receptor;

    public InvitacionAmistadId(Usuario emisor, Usuario receptor){
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public InvitacionAmistadId(){
        emisor = null;
        receptor = null;
    }
}