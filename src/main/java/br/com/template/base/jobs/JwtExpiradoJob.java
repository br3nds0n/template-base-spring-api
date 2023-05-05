package br.com.template.base.jobs;

import br.com.template.base.models.JwtToken;
import br.com.template.base.repositories.TokenRepository;
import br.com.template.base.services.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JwtExpiradoJob {

    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    public JwtExpiradoJob(TokenRepository tokenRepository, TokenService tokenService) {
        this.tokenService = tokenService;
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(fixedDelayString = "30000")
    public void deletarTokensExpirados() {
        log.info("JOB: deletar tokens expirados");

        List<JwtToken> expiredTokens = tokenRepository
                .findAll()
                .stream()
                .filter(jwtToken -> !tokenService.validarJwtToken(jwtToken.getValor()))
                .toList();

        tokenRepository.deleteAll(expiredTokens);

        log.info("Os seguintes tokens foram apagados {}", expiredTokens);
    }
}
