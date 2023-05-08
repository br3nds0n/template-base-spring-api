package br.com.template.base.services.impl;

import br.com.template.base.DTOs.request.CodigoConfirmacaoRequestDTO;
import br.com.template.base.enums.RoleEnum;
import br.com.template.base.enums.TokenEnum;
import br.com.template.base.exceptions.BadRequestException;
import br.com.template.base.exceptions.NotFoundException;
import br.com.template.base.models.JwtToken;
import br.com.template.base.models.Usuario;
import br.com.template.base.repositories.TokenRepository;
import br.com.template.base.repositories.UsuarioRepository;
import br.com.template.base.services.EmailService;
import br.com.template.base.services.TokenService;
import br.com.template.base.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final EmailService emailService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(EmailService emailService, TokenService tokenService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Usuario criarUsuario(Usuario usuario) {

        usuario.setEmailVerificado(false);
        usuario.setRole(RoleEnum.USER);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        usuarioRepository.save(usuario);

        JwtToken jwtToken = tokenService.criarToken(usuario, Duration.of(60000, ChronoUnit.MILLIS ), TokenEnum.ATIVAR_CONTA);

        emailService.enviarEmail(
                usuario.getEmail(),
                "Ativar Conta",
                 "Codigo de verificação: " + jwtToken.getCodigoVerificacao()
        );

        return usuario;
    }

    @Override
    public Usuario obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario não encontrado!"));
    }

    @Override
    public Usuario obterUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuario não encontrado!"));
    }

    @Override
    public Usuario ativarConta(CodigoConfirmacaoRequestDTO codigo) {
            Optional<JwtToken> optionalVerificationToken = tokenRepository.findByCodigoVerificacaoAndTokenTipo(codigo.getCodigo(), TokenEnum.ATIVAR_CONTA);

            if (optionalVerificationToken.isPresent()) {
                Usuario usuario = optionalVerificationToken.get().getUsuario();
                if (!tokenService.validarJwtToken(optionalVerificationToken.get().getValor())) {
                    throw new BadRequestException("Código expirado!");
                } else {
                    usuario.setEmailVerificado(true);
                    usuarioRepository.save(usuario);
                    tokenRepository.delete(optionalVerificationToken.get());
                }
                return usuarioRepository.save(usuario);
            }

            throw new BadRequestException("Sessão expirada!");
    }
}
