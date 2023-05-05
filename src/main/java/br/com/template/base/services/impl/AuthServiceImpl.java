package br.com.template.base.services.impl;

import br.com.template.base.DTOs.request.LoginRequestDTO;
import br.com.template.base.DTOs.response.AuthResponseDTO;
import br.com.template.base.exceptions.BadRequestException;
import br.com.template.base.models.Usuario;
import br.com.template.base.services.AuthService;
import br.com.template.base.services.TokenService;
import br.com.template.base.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(TokenService tokenService, UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    private Usuario obterUsuarioPrincipal(String email, String senha) {
        Usuario usuario = usuarioService.obterUsuarioPorEmail(email);

        if (usuario == null) throw new BadRequestException("E-mail ou Senha inválidos");
        if (!passwordEncoder.matches(senha, usuario.getSenha())) throw new BadRequestException("E-mail ou Senha inválidos");

        return usuario;
    }

    private AuthResponseDTO obterAuthResponse(Usuario usuario) {
        String accessToken = criarAccessToken(usuario);
        AuthResponseDTO authResponse = new AuthResponseDTO();
        authResponse.setAccessToken(accessToken);
//        addRefreshToken(user);
        return authResponse;
    }

    private AuthResponseDTO login(Usuario userPrincipal) {
        Usuario user = usuarioService.obterUsuarioPorEmail(userPrincipal.getEmail());
        return obterAuthResponse(user);
    }

    public String criarAccessToken(Usuario user) {
        return tokenService.criarValorTokenJwt(user);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        Usuario userPrincipal = obterUsuarioPrincipal(loginRequest.getEmail(), loginRequest.getSenha());
        return login(userPrincipal);
    }
}
