package com.cerea_p1.spring.jpa.postgresql.model.friends;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

@Entity
@Table(name = "amigo"//, 
         //   uniqueConstraints = { 
       //         @UniqueConstraint(columnNames = {"usuario1","usuario2"})
         //   }
)
@IdClass(AmigoId.class)
public class Amigo implements Serializable {
    @Id
  //  @Column(name = "usuario1", nullable = false)
    @ManyToOne
//    @JoinColumn(referencedColumnName = "nombre_de_usuario")
    private Usuario usuario1;
    @Id
  //  @Column(name = "usuario2", nullable = false)
    @ManyToOne
 //   @JoinColumn(referencedColumnName = "nombre_de_usuario")
    private Usuario usuario2;

  //  private AmigoId clave;
    

    public Amigo(Usuario u, Usuario u2) {
        usuario1 = u;
        usuario2 = u2;
    }

    public Amigo() {
        usuario1 = null;
        usuario2 = null;
    }

    public Usuario getUsuario1(){
        return usuario1;
    }

    public void setUsuario1(Usuario u){
        this.usuario1 = u;
    }

    public Usuario getUsuario2(){
        return usuario2;
    }

    public void setUsuario2(Usuario u){
        this.usuario2 = u;
    }

    @Override
    public String toString(){
        return "[username= " + this.usuario1 + "friendname= " + this.usuario2 + "]";
    }

    @Override
    public int hashCode() {
        return usuario1.hashCode() + usuario2.hashCode();
    }

    @Override
    public boolean equals(Object o){
        // If the object is compared with itself then return true 
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Amigo or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Amigo)) {
            return false;
        }

        // typecast o to Amigo so that we can compare data members
        Amigo a = (Amigo) o;

        return (a.getUsuario1().equals(this.usuario1) && a.getUsuario2().equals(this.usuario2)) || (a.getUsuario1().equals(this.usuario2) && a.getUsuario2().equals(this.usuario1));
    }
}


