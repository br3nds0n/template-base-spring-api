package br.com.template.base.services;

import br.com.template.base.DTOs.request.LoginRequestDTO;
import br.com.template.base.DTOs.response.AuthResponseDTO;
import br.com.template.base.models.Usuario;

public interface AuthService {

    AuthResponseDTO login(LoginRequestDTO loginRequest);
    void logout(Usuario usuario);
}
