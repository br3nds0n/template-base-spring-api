package br.com.template.base.services;

import br.com.template.base.DTOs.request.CodigoConfirmacaoRequestDTO;
import br.com.template.base.models.Usuario;

public interface UsuarioService {

    Usuario criarUsuario(Usuario usuario);
    Usuario obterUsuarioPorEmail(String email);
    Usuario ativarConta(CodigoConfirmacaoRequestDTO codigo);
}
