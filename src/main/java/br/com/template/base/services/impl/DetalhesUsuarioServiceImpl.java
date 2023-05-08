package br.com.template.base.services.impl;

import br.com.template.base.models.Usuario;
import br.com.template.base.repositories.UsuarioRepository;
import br.com.template.base.services.DetalhesUsuarioService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DetalhesUsuarioServiceImpl implements DetalhesUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public DetalhesUsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(usuario.get().getRole().toString()));

        return new User(email, usuario.get().getSenha(), authorities);
    }
}
