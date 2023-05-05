package br.com.template.base.DTOs.response;

import lombok.Getter;

@Getter
public class MensagemErroDTO {

    private int status;
    private String erro;
    private String descricao;

    public MensagemErroDTO(int status, String erro, String descricao) {
        this.status = status;
        this.erro = erro;
        this.descricao = descricao;
    }
}