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

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private TokenService tokenService;
  private PersonService personService;

  @Autowired
  public SecurityFilter(TokenService tokenService, PersonService personService) {
    this.tokenService = tokenService;
    this.personService = personService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = recoveryToken(request);

    if (token != null) {
      String subject = this.tokenService.validateToken(token);
    }
    filterChain.doFilter(request, response);
  }

  private void authenticate(String token) {
      String username = this.tokenService.validateToken(token);

      Person personFound = this.personService.getPersonByUsername(username);

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(personFound, null, personFound.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

  }

  public String recoveryToken(HttpServletRequest httpServletRequest) {
    String authHeader = httpServletRequest.getHeader("Authorization");

    if (authHeader == null) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }
}
