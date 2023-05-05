package br.com.template.base.services;

import br.com.template.base.DTOs.request.LoginRequestDTO;
import br.com.template.base.DTOs.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO login(LoginRequestDTO loginRequest);
}
