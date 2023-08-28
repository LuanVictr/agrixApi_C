package com.betrybe.agrix.ebytr.staff.controller.dto;

/**
 * Dto de token utilizado para criar a resposta da rota de Login.
 *
 * @param token token recebido por parametro
 */
public record TokenDto(String token) {
}
