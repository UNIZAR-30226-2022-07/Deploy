package com.cerea_p1.spring.jpa.postgresql.repository;

import java.util.List;
import com.cerea_p1.spring.jpa.postgresql.model.friends.Amigo;

import com.cerea_p1.spring.jpa.postgresql.model.friends.AmigoId;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AmigoRepository extends JpaRepository<Amigo,AmigoId> {

   List<Amigo> findByUsuario1(Usuario u);
   //Boolean existsByUsername(Usuario user);
  // Boolean existsByEmail(Usuario email);
}
