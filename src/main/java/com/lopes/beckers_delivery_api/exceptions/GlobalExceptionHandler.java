package com.lopes.beckers_delivery_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(MethodArgumentNotValidException ex) {

        // aqui eu junto os erros, caso mais de um campo nulo, por exemplo
        String erros = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(" "));

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), erros.trim()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(HttpMessageNotReadableException ex) {
        // mensagem padrãao, para caso não cair em nenhuma condição abaixo
        String message = "Erro na leitura do JSON.";

        String mensagemErro = ex.getMostSpecificCause().getMessage();

        // Regex para extrair o nome do campo
        Pattern pattern = Pattern.compile("\\[\\\"(\\w+)\\\"\\]");
        Matcher matcher = pattern.matcher(mensagemErro);

        String ultimoCampoDaMensagemErro = null;
        while (matcher.find()) {
            ultimoCampoDaMensagemErro = matcher.group(1); // guarda sempre o último campo encontrado
        }

        if (ultimoCampoDaMensagemErro != null) {
            message = "Valor inválido para o campo " + ultimoCampoDaMensagemErro + ".";
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(NotFoundException ex) {
        System.out.println("teste");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno: " + ex.getMessage()));
    }
}
