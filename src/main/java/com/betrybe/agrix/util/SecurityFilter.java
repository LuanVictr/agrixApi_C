package com.betrybe.agrix.util;

import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import com.betrybe.agrix.ebytr.staff.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Configura os filtros de seguranca para a utilizacao do token.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

  private TokenService tokenService;
  private PersonService personService;

  /**
   * Inicializa os filtros de seguranca para a utilizacao do token.
   *
   * @param tokenService recebe uma instancia da camada de servico de token
   * @param personService recebe uma instancia da camada de servico de person
   */
  @Autowired
  public SecurityFilter(TokenService tokenService, PersonService personService) {
    this.tokenService = tokenService;
    this.personService = personService;
  }

  /**
   * Configura o filtro para a utilizacao de tokens.
   *
   * @param request intercepta a request
   * @param response intercepta a response
   * @param filterChain recebe a cadeia de filtros
   * @throws ServletException em caso de erro no servlet
   * @throws IOException em caso de erro no I/O
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = recoveryToken(request);

    if (token != null) {
      String username = this.tokenService.validateToken(token);

      Person personFound = this.personService.getPersonByUsername(username);

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(personFound, null, personFound.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Funcao criada para extrair o token do header da requisicao.
   *
   * @param httpServletRequest requisicao recebida por parametro
   * @return retorna o token
   */
  public String recoveryToken(HttpServletRequest httpServletRequest) {
    String authHeader = httpServletRequest.getHeader("Authorization");

    if (authHeader == null) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }
}
