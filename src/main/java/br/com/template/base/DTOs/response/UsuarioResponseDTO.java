package br.com.template.base.DTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Boolean emailVerificado;
    private String role;
}
