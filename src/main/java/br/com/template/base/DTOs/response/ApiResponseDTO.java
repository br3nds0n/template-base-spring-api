package br.com.template.base.DTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO {

    private boolean sucesso;
    private String mensagem;

    public ApiResponseDTO(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }
}
