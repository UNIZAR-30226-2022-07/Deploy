package com.cerea_p1.spring.jpa.postgresql.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.cerea_p1.spring.jpa.postgresql.model.Usuario;
import com.cerea_p1.spring.jpa.postgresql.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsuarioRepository userRepository;

	@Override
	//  @Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Usuario user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
			if (!user.getActiva()) throw new UsernameNotFoundException("La cuenta del usuario no está activa.");
		return UserDetailsImpl.build(user);
	}

    public void updateResetPasswordToken(String token, String email) throws EntityNotFoundException {
		Usuario user= userRepository.findByEmail(email);
		if (user != null) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Could not find any customer with the email " + email);
		}
	}
  
	public Usuario getByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}
  
	public void updatePassword(Usuario user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		
		user.setResetPasswordToken(null);
		userRepository.save(user);
	}

	public void activarCuenta(String username, String token){
		Optional<Usuario> user= userRepository.findByUsername(username);
		if(user.isPresent()){
			Usuario u = user.get();
			if(token.equals(u.getRegistroToken())) {
				u.setActiva();
				u.setRegistroToken("");
				userRepository.save(u);
			} else {
				throw new EntityNotFoundException("Los tokens no coinciden");
			}
		}
	}
}