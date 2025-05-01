package com.lopes.beckers_delivery_api.controllers;

import com.lopes.beckers_delivery_api.dtos.DadosUsuarioRecordDto;
import com.lopes.beckers_delivery_api.dtos.DadosUsuarioResponseDto;
import com.lopes.beckers_delivery_api.dtos.UsuarioResponseDto;
import com.lopes.beckers_delivery_api.mappers.DadosUsuarioMapper;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DadosUsuarioMapper dadosUsuarioMapper;

    @PostMapping
    public ResponseEntity<?> saveUsuario(@RequestBody @Valid DadosUsuarioRecordDto dadosUsuarioRecordDto) {

        UsuarioModel usuarioModel = usuarioService.saveUsuarioService(dadosUsuarioRecordDto);

        DadosUsuarioResponseDto dadosUsuarioResponseDto = dadosUsuarioMapper.toDto(usuarioModel);

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto(dadosUsuarioResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDto);
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
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioResponseDto);
    }
}
