package com.lopes.beckers_delivery_api.controllers;

import com.lopes.beckers_delivery_api.dtos.EnderecoRecordDto;
import com.lopes.beckers_delivery_api.models.EnderecoModel;
import com.lopes.beckers_delivery_api.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{id}/enderecos")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<?> saveEnderecos(@PathVariable(value = "id") Long id,
                                          @RequestBody @Valid List<EnderecoRecordDto> enderecoRecordDto){
        System.out.println("CHEGUEI NO CONTROLLER! ID = " + id);
        List<EnderecoModel> enderecosModel = enderecoService.saveEnderecoService(id, enderecoRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
