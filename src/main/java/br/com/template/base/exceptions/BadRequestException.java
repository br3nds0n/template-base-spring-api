package br.com.template.base.exceptions;

public class BadRequestException extends AbstractException {

    public BadRequestException(String descricao) {
        super("BadRequest", descricao);
    }
}