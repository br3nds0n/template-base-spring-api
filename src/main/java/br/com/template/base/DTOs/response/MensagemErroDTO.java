package br.com.template.base.DTOs.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MensagemErroDTO {

    private int status;
    private String erro;
    private String descricao;
    private List<String> erros;

    public MensagemErroDTO(int status, String erro, String descricao) {
        this.status = status;
        this.erro = erro;
        this.descricao = descricao;
    }

    public MensagemErroDTO(int status, List<String> erros, String descricao) {
        this.status = status;
        this.erros = erros;
        this.descricao = descricao;
    }
}