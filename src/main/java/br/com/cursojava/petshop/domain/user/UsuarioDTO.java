package br.com.cursojava.petshop.domain.user;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String login;
    private String senha;
}