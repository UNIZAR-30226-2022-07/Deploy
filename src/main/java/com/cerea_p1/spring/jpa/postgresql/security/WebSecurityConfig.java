package com.cerea_p1.spring.jpa.postgresql.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;

import com.cerea_p1.spring.jpa.postgresql.security.jwt.AuthEntryPointJwt;
import com.cerea_p1.spring.jpa.postgresql.security.jwt.AuthTokenFilter;
import com.cerea_p1.spring.jpa.postgresql.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/api/auth/*").permitAll().anyRequest().authenticated().and().httpBasic().and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/api/auth/signin?invalid-session=true");
		// http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/api/auth/signin").and().authorizeRequests().antMatchers("/api/auth/**").permitAll().antMatchers("/api/test/**").permitAll().anyRequest().authenticated().and().httpBasic();//.and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/api/auth/signin?invalid-session=true");
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void setContentNegotationStrategy(org.springframework.web.accept.ContentNegotiationStrategy contentNegotiationStrategy){
		super.setContentNegotationStrategy(contentNegotiationStrategy);
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
//	@Bean
//	@Bean
	// @Override
	// @SuppressWarnings("deprecation")
	// public UserDetailsService userDetailsService() {


	// 	return super.userDetailsService();
	// }
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService(){
//		return super.userDetailsService();
//	}
}
