package com.betrybe.agrix.ebytr.staff.controller.dto;

import com.betrybe.agrix.ebytr.staff.entity.Person;

/**
 * Cadamada Dto de Person, utilizado para receber informacoes de pessoas novas.
 *
 * @param username nome de usuario da pessoa nova
 * @param password senha da pessoa nova
 * @param role role da pessoa nova
 */
public record PersonDto(String username, String password, String role) {

  /**
   * Transforma a Dto em uma nova instancia de Person.
   *
   * @return retorna uma instancia nova de Person
   */
  public Person toPerson() {
    return new Person(username, password, role);
  }
}
