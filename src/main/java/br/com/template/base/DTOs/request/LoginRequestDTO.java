package br.com.template.base.DTOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "E-mail não pode ser vazio!")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia!")
    private String password;
}
