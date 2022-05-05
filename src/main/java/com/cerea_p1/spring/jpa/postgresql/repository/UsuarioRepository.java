package com.cerea_p1.spring.jpa.postgresql.repository;

import java.util.List;
import java.util.Optional;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String> {
    Optional<Usuario> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("SELECT username, puntos FROM Usuario u WHERE u.pais = :pais ORDER BY puntos DESC")
    List<String> userRankingByPais(@Param("pais") String pais);
    @Query("SELECT username, puntos, pais FROM Usuario u ORDER BY puntos DESC")
    List<String> userRankingMundial();
    @Query("SELECT u2.username, u2.puntos, u2.pais FROM Usuario u, Amigo a, Usuario u2 WHERE u.username = a.usuario1 and u2.username = a.usuario2 ORDER BY puntos DESC")
    List<String> userRankingAmigos(@Param("username") String username);
}
