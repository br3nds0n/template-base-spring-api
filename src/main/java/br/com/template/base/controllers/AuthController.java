package br.com.template.base.controllers;

import br.com.template.base.DTOs.request.CodigoConfirmacaoRequestDTO;
import br.com.template.base.DTOs.request.LoginRequestDTO;
import br.com.template.base.DTOs.request.SignUpRequestDTO;
import br.com.template.base.DTOs.response.ApiResponseDTO;
import br.com.template.base.DTOs.response.AuthResponseDTO;
import br.com.template.base.models.Usuario;
import br.com.template.base.services.AuthService;
import br.com.template.base.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    protected AuthController(ModelMapper modelMapper, AuthService authService, UsuarioService usuarioService) {
        super(modelMapper);
        this.authService = authService;
        this.usuarioService = usuarioService;
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
