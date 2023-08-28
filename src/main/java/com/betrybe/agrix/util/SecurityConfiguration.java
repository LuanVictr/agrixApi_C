package com.betrybe.agrix.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Segunda camada de configuracao do spring security.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

  private SecurityFilter securityFilter;

  @Autowired
  public SecurityConfiguration(SecurityFilter securityFilter) {
    this.securityFilter = securityFilter;
  }

  /**
   * Bean que inicializa a cadeia de filtros.
   *
   * @param httpSecurity recebe uma instancia de httpSecurity por injecao de dependencia
   * @return retorna uma cadeida de filtros buildada
   * @throws Exception exception lancada em caso de falha da desativacao de csrf
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session
            -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize ->
            authorize.requestMatchers(HttpMethod.POST, "/persons").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated()
        )
        .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  /**
   * Bean que inicializa uma instancia de authenticationmanager.
   *
   * @param authenticationConfiguration recebe a instancia do configurador de authentication
   * @return retorna um authenticationmanager
   * @throws Exception retorna uma excecao em caso de falha
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Cria uma instancia do BCryptPassowrdEncoder para codificar passwords.
   *
   * @return retorna a instancia inicializada
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
