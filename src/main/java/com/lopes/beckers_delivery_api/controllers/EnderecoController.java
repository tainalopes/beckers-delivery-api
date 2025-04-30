package com.lopes.beckers_delivery_api.controllers;

import com.lopes.beckers_delivery_api.dtos.ListaEnderecosDto;
import com.lopes.beckers_delivery_api.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{id}/enderecos")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;
    ListaEnderecosDto listaEnderecosDto;

    @PostMapping
    public ResponseEntity<?> saveEnderecos(@PathVariable(value = "id") Long id,
                                          @RequestBody @Valid ListaEnderecosDto listaEnderecosDtos){
        enderecoService.saveEnderecoService(id, listaEnderecosDtos.getEnderecos());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
