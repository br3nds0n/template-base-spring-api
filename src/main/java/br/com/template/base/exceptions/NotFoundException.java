package br.com.template.base.exceptions;

public class NotFoundException extends AbstractException {

    public NotFoundException(String descricao) {
        super("NotFound", descricao);
    }
}
