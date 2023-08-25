package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.PersonDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.PersonResponseDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cria a camada de controle para as rotas /person
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

  private PersonService personService;
  private AuthenticationManager authenticationManager;

  @Autowired
  public PersonController(PersonService personService,
      AuthenticationManager authenticationManager) {

    this.personService = personService;
    this.authenticationManager = authenticationManager;

  }

  @PostMapping
  public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonDto personDto) {

    Person personToCreate = personDto.toPerson();

    Person createdPerson = this.personService.create(personToCreate);

    PersonResponseDto createdPersonResponse = new PersonResponseDto(createdPerson.getId(),
        createdPerson.getUsername(), personDto.role());

    return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonResponse);
  }


}
