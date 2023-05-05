package br.com.template.base.services;

import br.com.template.base.enums.TokenEnum;
import br.com.template.base.models.JwtToken;
import br.com.template.base.models.Usuario;

import java.time.Duration;

public interface TokenService {

   String obterEmailpeloToken(String token);
   String criarValorTokenJwt(Usuario usuario, Duration expiraEm);
   JwtToken criarToken(Usuario user, Duration expiraEm, TokenEnum tokenTipo);
   boolean validarJwtToken(String jwtToken);
   void deletarToken(JwtToken jwtToken);
}
