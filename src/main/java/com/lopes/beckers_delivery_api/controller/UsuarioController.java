package com.lopes.beckers_delivery_api.controller;

import com.lopes.beckers_delivery_api.dtos.UsuarioRecordDto;
import com.lopes.beckers_delivery_api.exceptions.ErrorResponse;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import com.lopes.beckers_delivery_api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/usuario")
    public ResponseEntity<?> saveUsuario(@RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        try {
            // verifica se já existe um usuário com o mesmo email
            if (usuarioRepository.findByEmail(usuarioRecordDto.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Já existe um usuário com o email " + usuarioRecordDto.email() + "."));
            }

            // verifica se já existe um usuário com o mesmo CPF
            if (usuarioRepository.findByCpf(usuarioRecordDto.cpf()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Já existe um usuário com o CPF " + usuarioRecordDto.cpf() + "."));
            }

            var usuarioModel = new UsuarioModel();
            BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);

            // salva o novo usuário
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));
        } catch (Exception e) {
            // captura qualquer exceção inesperada e retorna um erro 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping("/usuario")
    // lista todos os usuários cadastrados
    public ResponseEntity<List<UsuarioModel>> getAllUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll());
    }

    @GetMapping("/usuario/{id}")
    // lista um usuário buscando por id
    public ResponseEntity<Object> getUsuarioById(@PathVariable(value = "id")UUID id){
        Optional<UsuarioModel> usuario0 = usuarioRepository.findById(id);

        // verifica se usuário existe
        if (usuario0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuario0.get());
    }
}
