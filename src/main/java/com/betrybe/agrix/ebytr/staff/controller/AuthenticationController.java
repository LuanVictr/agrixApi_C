package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.LoginPerson;
import com.betrybe.agrix.ebytr.staff.entity.Person;
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

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private AuthenticationManager authenticationManager;

  private TokenService tokenService;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginPerson> authenticatePerson(@RequestBody LoginPerson loginPerson) {

    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(loginPerson.username(), loginPerson.password());

    Authentication auth = this.authenticationManager.authenticate(usernamePassword);

    return ResponseEntity.status(HttpStatus.OK).body(loginPerson);
  }

  @GetMapping
  public ResponseEntity help() {
    return ResponseEntity.status(HttpStatus.OK).body("Saudavel");
  }

}
