package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.LoginPerson;
import com.betrybe.agrix.ebytr.staff.controller.dto.TokenDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.exception.IncorrectPasswordException;
import com.betrybe.agrix.ebytr.staff.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Camada de controle das rotas /auth.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private AuthenticationManager authenticationManager;

  private TokenService tokenService;

  /**
   * Construtor da camada de controle das rotas /auth.
   *
   * @param authenticationManager authenticationmanager recebido por injecao de dependencia
   * @param tokenService instancia da camada de servico de token recebido por injecao de dependencia
   */
  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * Rota POST /login, utilizada para logar um usuario ja criado.
   *
   * @param loginPerson recebe a Dto de loginPerson com as informacoes do login
   * @return retorna um token gerado
   */
  @PostMapping("/login")
  public ResponseEntity authenticatePerson(@RequestBody LoginPerson loginPerson) {

    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(loginPerson.username(), loginPerson.password());

    Authentication auth = this.authenticationManager.authenticate(usernamePassword);

    Person person = (Person) auth.getPrincipal();
    String token = this.tokenService.generateToken(person);
    TokenDto tokenDto = new TokenDto(token);

    return ResponseEntity.status(HttpStatus.OK).body(tokenDto);

  }

}
