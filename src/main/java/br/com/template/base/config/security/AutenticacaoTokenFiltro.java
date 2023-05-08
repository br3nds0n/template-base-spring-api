package br.com.template.base.config.security;

import br.com.template.base.services.DetalhesUsuarioService;
import br.com.template.base.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class AutenticacaoTokenFiltro extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final DetalhesUsuarioService detalhesUsuarioService;

    public AutenticacaoTokenFiltro(TokenService tokenService, DetalhesUsuarioService detalhesUsuarioService) {
        this.tokenService = tokenService;
        this.detalhesUsuarioService = detalhesUsuarioService;
    }

    private Optional<String> obterAcessoJwt(HttpServletRequest request) {
        Optional<String> optionalToken = Optional.empty();
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7, bearerToken.length()));
        }
        return optionalToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email = null;
                String jwt = obterAcessoJwt(request).orElse(null);
        if (jwt != null) {
            try {
                 email = tokenService.obterEmailpeloToken(jwt);
            } catch (IllegalArgumentException e) {
                System.out.println("Nao foi possível obter o token JWT");
            } catch (ExpiredJwtException e) {
                System.out.println("O token JWT expirou");
            }

            UserDetails userDetails = detalhesUsuarioService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
