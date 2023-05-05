package br.com.template.base.repositories;

import br.com.template.base.models.JwtToken;
import br.com.template.base.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<JwtToken, Long> {

    Optional<JwtToken> findByUsuario(Usuario usuario);
    Optional<JwtToken> findByValor(String value);
}
