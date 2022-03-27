package com.cerea_p1.spring.jpa.postgresql.repository;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String> {
    Usuario findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
