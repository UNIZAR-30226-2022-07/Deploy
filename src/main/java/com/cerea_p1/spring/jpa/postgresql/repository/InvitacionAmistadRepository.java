package com.cerea_p1.spring.jpa.postgresql.repository;

import java.util.Optional;
import com.cerea_p1.spring.jpa.postgresql.model.friends.InvitacionAmistad;
import com.cerea_p1.spring.jpa.postgresql.model.friends.InvitacionAmistadId;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvitacionAmistadRepository extends JpaRepository<InvitacionAmistad,InvitacionAmistadId> {
   List<InvitacionAmistad> findByReceptor(Usuario u);
  // Boolean existsByUsername(Usuario user);
}