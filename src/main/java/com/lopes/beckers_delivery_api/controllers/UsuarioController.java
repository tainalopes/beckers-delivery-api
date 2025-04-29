package com.lopes.beckers_delivery_api.controllers;

import com.lopes.beckers_delivery_api.dtos.DadosUsuarioRecordDto;
import com.lopes.beckers_delivery_api.dtos.DadosUsuarioResponseDto;
import com.lopes.beckers_delivery_api.dtos.EnderecoResponseDto;
import com.lopes.beckers_delivery_api.dtos.UsuarioResponseDto;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> saveUsuario(@RequestBody @Valid DadosUsuarioRecordDto dadosUsuarioRecordDto) {

        UsuarioModel usuarioModel = usuarioService.saveUsuarioService(dadosUsuarioRecordDto);
        List<EnderecoResponseDto> enderecosDto = usuarioModel.getEnderecos().stream()
                .map(endereco -> new EnderecoResponseDto(
                        endereco.getLogradouro(),
                        endereco.getNumero(),
                        endereco.getComplemento(),
                        endereco.getBairro(),
                        endereco.getCep(),
                        endereco.getCidade(),
                        endereco.getEstado()
                )).collect(Collectors.toList());

        DadosUsuarioResponseDto dadosUsuarioResponseDto = new DadosUsuarioResponseDto(
                usuarioModel.getId(),
                usuarioModel.getNome(),
                usuarioModel.getEmail(),
                usuarioModel.getCpf(),
                enderecosDto
        );

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto(dadosUsuarioResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsuarios(@RequestParam("pagina") int page,
                                                                   @RequestParam("itens") int itens) {
        List<UsuarioResponseDto> usuarioResponseDtos = usuarioService.getAllUsuariosService(page, itens);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable(value = "id") Long id) {
            Object usuarioResponseDto = usuarioService.getUsuarioByIdService(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuarioResponseDto);
    }
}
