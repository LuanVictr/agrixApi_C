package com.betrybe.agrix.ebytr.staff.exception;

public class IncorrectPasswordException extends Exception {

  public IncorrectPasswordException() {
    super("Senha Incorreta");
  }
}
