package br.com.template.base.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DetalhesUsuarioService extends UserDetailsService {

    UserDetails loadUserByUsername(String email);
}
