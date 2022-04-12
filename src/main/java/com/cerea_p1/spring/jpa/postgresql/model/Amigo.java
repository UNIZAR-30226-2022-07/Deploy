package com.cerea_p1.spring.jpa.postgresql.model;

<<<<<<< HEAD
// import javax.persistence.*;
// import javax.validation.constraints.*;

// import java.io.Serializable;
// import java.util.*;

// @Entity
// @Table(name = "amigo", 
//             uniqueConstraints = { 
//                 @UniqueConstraint(columnNames = {"usuario1","usuario2"})
//             }
// )
// @IdClass(Amigo.class)
// public class Amigo implements Serializable {
//     @Id
//     @Column(name = "usuario1", nullable = false, length = 255)
//     private String usuario1;
//     @Id
//     @Column(name = "usuario2", nullable = false, length = 255)
//     private String usuario2;
    

//     public Amigo(String u, String u2) {
//         usuario1 = u;
//         usuario2 = u2;
//     }

//     public Amigo() {
//         usuario1 = null;
//         usuario2 = null;
//     }

//     public String getUsuario1(){
//         return usuario1;
//     }

//     public void setUsuario1(String u){
//         this.usuario1 = u;
//     }

//     public String getUsuario2(){
//         return usuario2;
//     }

//     public void setUsuario2(String u){
//         this.usuario2 = u;
//     }

//     @Override
//     public String toString(){
//         return "[username= " + this.usuario1 + "friendname= " + this.usuario2 + "]";
//     }

//     @Override
//     public int hashCode() {
//         return usuario1.hashCode() + usuario2.hashCode();
//     }

//     @Override
//     public boolean equals(Object o){
//         // If the object is compared with itself then return true 
//         if (o == this) {
//             return true;
//         }

//         /* Check if o is an instance of Amigo or not
//           "null instanceof [type]" also returns false */
//         if (!(o instanceof Amigo)) {
//             return false;
//         }

//         // typecast o to Amigo so that we can compare data members
//         Amigo a = (Amigo) o;

//         return (a.getUsuario1().equals(this.usuario1) && a.getUsuario2().equals(this.usuario2)) || (a.getUsuario1().equals(this.usuario2) && a.getUsuario2().equals(this.usuario1));
//     }
// }
=======
import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "amigo", 
            uniqueConstraints = { 
                @UniqueConstraint(columnNames = {"usuario1","usuario2"})
            }
)
@IdClass(Amigo.class)
public class Amigo implements Serializable{
    @Id
    @Column(name = "usuario1", nullable = false, length = 255)
    private String usuario1;
    @Id
    @Column(name = "usuario2", nullable = false, length = 255)
    private String usuario2;
    

    public Amigo(String u, String u2) {
        usuario1 = u;
        usuario2 = u2;
    }

    public Amigo() {
    }

    public String getUsuario1(){
        return usuario1;
    }

    public void setUsuario1(String u){
        this.usuario1 = u;
    }

    public String getUsuario2(){
        return usuario2;
    }

    public void setUsuario2(String u){
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
}
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
