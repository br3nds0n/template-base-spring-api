package br.com.template.base.services.impl;

import br.com.template.base.enums.Role;
import br.com.template.base.exceptions.NotFoundException;
import br.com.template.base.models.Usuario;
import br.com.template.base.repositories.UsuarioRepository;
import br.com.template.base.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario criarUsuario(Usuario usuario) {

        usuario.setEmailVerificado(false);
        usuario.setRole(Role.USER);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obterUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuario não encontrado!"));
    }
}
