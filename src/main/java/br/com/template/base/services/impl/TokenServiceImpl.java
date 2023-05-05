package br.com.template.base.services.impl;

import br.com.template.base.models.JwtToken;
import br.com.template.base.models.Usuario;
import br.com.template.base.repositories.TokenRepository;
import br.com.template.base.services.TokenService;
import io.jsonwebtoken.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private static final Duration EXPIRA_EM = Duration.ofHours(30000);
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    private String criarValorTokenJwt(Usuario usuario, Map<String, Object> claims) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + EXPIRA_EM.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getNome())
                .setIssuedAt(new Date())
                .setExpiration(dataExpiracao)
                .setIssuer("spring template")
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String criarValorTokenJwt(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", usuario.getRole());
        return criarValorTokenJwt(usuario, claims);
    }

    @Override
    @Transactional
    public JwtToken criarToken(Usuario usuario) {
        String tokenValor = criarValorTokenJwt(usuario);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setValor(tokenValor);
        jwtToken.setUsuario(usuario);

        return tokenRepository.save(jwtToken);
    }

    @Override
    public boolean validarJwtToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Assinatura JWT inválida");
        } catch (MalformedJwtException ex) {
            log.error("Token JWT inválido");
        } catch (ExpiredJwtException ex) {
            log.error("Token JWT expirado");
        } catch (UnsupportedJwtException ex) {
            log.error("Token JWT não compatível");
        } catch (IllegalArgumentException ex) {
            log.error("A string de declarações JWT está vazia.");
        }
        return false;
    }

    @Override
    public void deletarToken(JwtToken jwtToken) {
        tokenRepository.delete(jwtToken);
    }
}
