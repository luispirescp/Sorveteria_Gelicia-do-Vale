package br.com.cursojava.petshop.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Erro ao processar a requisição: os dados enviados são inválidos ou estão em um formato incorreto.";
        // Personalize a mensagem de erro conforme necessário

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    // Outros métodos para manipulação de exceções podem ser adicionados aqui
}

