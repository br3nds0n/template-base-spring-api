package br.com.template.base.DTOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {

    @Size(min = 4, message = "Nome deve ter no mínimo 4 caracteres!")
    private String nome;

    @Email(message = "E-mail inválido!")
    private String email;

    @Pattern(message = "Formato de senha inválido", regexp = "^(?=.*[\\d])(?=.*[A-Z])(?=.*[a-z])[\\w!@#$%^&*]{8,}$")
    private String senha;
}
