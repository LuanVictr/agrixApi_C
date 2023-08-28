package com.betrybe.agrix.ebytr.staff.controller.dto;

/**
 * Camada Dto para a resposta da rota de criacao POST /persons, que oculta a password criada.
 *
 * @param id id da pessoa criada
 * @param username username da pessoa criada
 * @param role role da pessoa criada
 */
public record PersonResponseDto(Long id, String username, String role) {
}
