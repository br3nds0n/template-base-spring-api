package br.com.template.base.controllers;

import br.com.template.base.DTOs.request.CodigoConfirmacaoRequestDTO;
import br.com.template.base.DTOs.request.LoginRequestDTO;
import br.com.template.base.DTOs.request.SignUpRequestDTO;
import br.com.template.base.DTOs.response.ApiResponseDTO;
import br.com.template.base.DTOs.response.AuthResponseDTO;
import br.com.template.base.models.Usuario;
import br.com.template.base.services.AuthService;
import br.com.template.base.services.TokenService;
import br.com.template.base.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    protected AuthController(ModelMapper modelMapper, AuthService authService, UsuarioService usuarioService, TokenService tokenService) {
        super(modelMapper);
        this.authService = authService;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/validarToken/{token}")
    @Operation(tags = "Auth", summary = "Validar token")
    public ResponseEntity<ApiResponseDTO> validarToken(@PathVariable String token) {
        boolean valido = tokenService.validarJwtToken(token);
        return new ResponseEntity<>(new ApiResponseDTO(valido, valido ? "Token inválido" : "Token válido"), HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(tags = "Auth", summary = "Autenticar usuário")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDto) {

        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/signup")
    @Operation(tags = "Auth", summary = "Cria um novo usuário")
    public ResponseEntity<ApiResponseDTO> cadastrarUsuario(@Valid @RequestBody SignUpRequestDTO signUpRequestDto) {

        usuarioService.criarUsuario(mapearDTO(signUpRequestDto, Usuario.class));
        return new ResponseEntity<>(new ApiResponseDTO(true, "Usuário criado com sucesso!"), HttpStatus.CREATED);
    }

    @PostMapping("/ativar-conta")
    @Operation(tags = "Auth", summary = "Ativa conta do usuário")
    public ResponseEntity<ApiResponseDTO> activateUserAccount(@RequestBody CodigoConfirmacaoRequestDTO codigo) {
        usuarioService.ativarConta(codigo);
        return new ResponseEntity<>(new ApiResponseDTO(true, "Conta verificada com sucesso!"), HttpStatus.OK);
    }
}
