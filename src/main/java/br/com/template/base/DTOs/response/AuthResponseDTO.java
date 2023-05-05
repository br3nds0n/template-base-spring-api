package br.com.template.base.DTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {

    private String accessToken;
    private Boolean twoFactorRequired;
    private String tokenType = "Bearer";
    private String message;
}
