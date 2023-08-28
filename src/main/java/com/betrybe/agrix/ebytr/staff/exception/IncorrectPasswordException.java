package com.betrybe.agrix.ebytr.staff.exception;

/**
 * Exception customizada criada para informar quando a senha estiver incorreta.
 */
public class IncorrectPasswordException extends Exception {

  public IncorrectPasswordException() {
    super("Senha Incorreta");
  }
}
