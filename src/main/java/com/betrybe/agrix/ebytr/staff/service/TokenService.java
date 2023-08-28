package com.betrybe.agrix.ebytr.staff.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servico de geracao e autenticacao dos tokens.
 */
@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  /**
   * Funcao que gera um token a partir de uma Person.
   *
   * @param person pessoa passada por parametro
   * @return retorna um token gerado
   */
  public String generateToken(Person person) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
        .withIssuer("agrix")
        .withExpiresAt(expiresAt())
        .withSubject(person.getUsername())
        .sign(algorithm);
  }

  /**
   * Funcao que gera uma data de Expiracao do token.
   *
   * @return retorna um instante em que o token expira
   */
  public Instant expiresAt() {
    LocalDateTime localDateTime = LocalDateTime.now();
    return localDateTime.plusHours(48).toInstant(ZoneOffset.of("-03:00"));
  }

  /**
   * Funcao que valida o token, checando se ele e valido e retornando o seu subject.
   *
   * @param token recebe o token a ser checado
   * @return retorna o subject encriptado no token
   */
  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm)
        .withIssuer("agrix")
        .build()
        .verify(token)
        .getSubject();
  }

}
