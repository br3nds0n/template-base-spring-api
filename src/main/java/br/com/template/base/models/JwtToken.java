package br.com.template.base.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "jwt_token_seq")
    @SequenceGenerator(name = "jwt_token_seq", allocationSize = 1)
    private Long id;

    private String valor;

    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

}
