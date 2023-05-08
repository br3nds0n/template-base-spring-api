package br.com.template.base.controllers;


import br.com.template.base.DTOs.response.UsuarioResponseDTO;
import br.com.template.base.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController extends AbstractController {

    private final UsuarioService usuarioService;

    public UsuarioController(ModelMapper modelMapper, UsuarioService usuarioService) {
        super(modelMapper);
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    @Operation(tags = "Usuario", summary = "Obter usuario por id")
    public ResponseEntity<UsuarioResponseDTO> obterUsuarioPorId(@PathVariable Long id, Authentication authentication) {
        return new ResponseEntity<>(mapearDTO(usuarioService.obterUsuarioPorId(id), UsuarioResponseDTO.class), HttpStatus.OK);
    }
}
