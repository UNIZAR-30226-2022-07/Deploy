package com.cerea_p1.spring.jpa.postgresql.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cerea_p1.spring.jpa.postgresql.model.Usuario;
import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UsuarioRepository userRepository;

  @Override
<<<<<<< HEAD
//  @Transactional
=======
  @Transactional
>>>>>>> 2d82ce93606e4d21272f91fd802041c5f9a602f2
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    return UserDetailsImpl.build(user);
  }
}