package br.com.template.base.services.impl;

import br.com.template.base.enums.RoleEnum;
import br.com.template.base.enums.TokenEnum;
import br.com.template.base.exceptions.NotFoundException;
import br.com.template.base.models.JwtToken;
import br.com.template.base.models.Usuario;
import br.com.template.base.repositories.UsuarioRepository;
import br.com.template.base.services.TokenService;
import br.com.template.base.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(TokenService tokenService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario criarUsuario(Usuario usuario) {

        usuario.setEmailVerificado(false);
        usuario.setRole(RoleEnum.USER);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        usuarioRepository.save(usuario);

        tokenService.criarToken(usuario, Duration.of(60000, ChronoUnit.MILLIS ), TokenEnum.ATIVAR_CONTA);

        return usuario;
    }

    @Override
    public Usuario obterUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuario não encontrado!"));
    }
}
