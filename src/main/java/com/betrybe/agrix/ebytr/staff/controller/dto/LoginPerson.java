package com.betrybe.agrix.ebytr.staff.controller.dto;

/**
 * Camada Dto para receber as informacoes de login de uma pessoa.
 *
 * @param username nome de usuario a ser logado
 * @param password senha do usuario a ser logado
 */
public record LoginPerson(String username, String password) {
}
