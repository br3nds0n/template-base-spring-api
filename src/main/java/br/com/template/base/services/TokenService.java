package br.com.template.base.services;

import br.com.template.base.models.JwtToken;
import br.com.template.base.models.Usuario;

public interface TokenService {

   String criarValorTokenJwt(Usuario usuario);
   JwtToken criarToken(Usuario user);
   boolean validarJwtToken(String jwtToken);
   void deletarToken(JwtToken jwtToken);
}
