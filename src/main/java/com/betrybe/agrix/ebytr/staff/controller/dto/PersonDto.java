package com.betrybe.agrix.ebytr.staff.controller.dto;

import com.betrybe.agrix.ebytr.staff.entity.Person;

public record PersonDto(String username, String password, String role) {

  public Person toPerson() {
    return new Person(username, password, role);
  }
}
