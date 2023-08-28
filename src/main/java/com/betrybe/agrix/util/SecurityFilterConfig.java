package com.betrybe.agrix.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Camada de configuracao do spring security.
 */
@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {

  private SecurityFilter securityFilter;

  /**
   * Construtor da camada de configuracao.
   *
   * @param securityFilter recebe um filter por injecao de dependencia
   */
  @Autowired
  public SecurityFilterConfig(SecurityFilter securityFilter) {
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
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

}
