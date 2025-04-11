package com.lopes.beckers_delivery_api.controllers;

import com.lopes.beckers_delivery_api.dtos.DadosUsuarioRecordDto;
import com.lopes.beckers_delivery_api.dtos.UsuarioResponseDto;
import com.lopes.beckers_delivery_api.exceptions.ErrorResponse;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    //UsuarioRepository usuarioRepository;
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> saveUsuario(@RequestBody @Valid DadosUsuarioRecordDto dadosUsuarioRecordDto) {

        try {
            UsuarioModel usuarioModel = usuarioService.saveUsuarioService(dadosUsuarioRecordDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(dadosUsuarioRecordDto);

            } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));

        } catch (Exception e) {
            // captura qualquer exceção inesperada e retorna um erro 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsuarios(@RequestParam("pagina") int page,
                                                                   @RequestParam("itens") int itens){
        List<UsuarioResponseDto> usuarioResponseDtos = usuarioService.getAllUsuariosService(page, itens);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable(value = "id") UUID id){
        try{
        Object usuarioResponseDto = usuarioService.getUsuarioByIdService(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuarioResponseDto);

        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));

        } catch (Exception e) {
            // captura qualquer exceção inesperada e retorna um erro 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }
}
