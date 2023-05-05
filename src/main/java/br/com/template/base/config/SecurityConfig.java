package br.com.template.base.config;

import br.com.template.base.config.security.AutenticacaoTokenFiltro;
import br.com.template.base.config.security.RestAuthenticationEntryPoint;
import br.com.template.base.services.DetalhesUsuarioService;
import br.com.template.base.services.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final TokenService tokenService;
    private final DetalhesUsuarioService detalhesUsuarioService;

    public SecurityConfig(TokenService tokenService, DetalhesUsuarioService detalhesUsuarioService) {
        this.tokenService = tokenService;
        this.detalhesUsuarioService = detalhesUsuarioService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AutenticacaoTokenFiltro tokenAuthenticationFilter() {
        return new AutenticacaoTokenFiltro(tokenService, detalhesUsuarioService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authorizeHttpRequests(requests -> {
                            try {
                                requests
                                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                        .requestMatchers("/api/**").authenticated()
                                        .anyRequest().permitAll()
                                        .and().cors().and()
                                        .exceptionHandling()
                                        .authenticationEntryPoint(new RestAuthenticationEntryPoint());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
