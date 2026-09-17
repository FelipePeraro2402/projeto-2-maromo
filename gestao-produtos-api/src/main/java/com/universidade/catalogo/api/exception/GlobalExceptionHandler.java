package com.universidade.catalogo.api.exception;

import com.universidade.catalogo.domain.exception.RecursoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail tratarRecursoNaoEncontradoException(
            RecursoNaoEncontradoException ex
    ) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problemDetail.setTitle("Recurso Não Encontrado");

        problemDetail.setType(URI.create(
                "https://api.universidade.com/erros/recurso-nao-encontrado"
        ));

        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail tratarMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Um ou mais campos contêm erros de validação de contrato."
        );

        problemDetail.setTitle("Violação de Validação de Dados");

        problemDetail.setType(URI.create(
                "https://api.universidade.com/erros/dados-invalidos"
        ));

        Map<String, String> camposComErro = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            camposComErro.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }

        problemDetail.setProperty("erros", camposComErro);
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail tratarExcecoesNaoMapeadas(Exception ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado no servidor."
        );

        problemDetail.setTitle("Erro Interno do Servidor");

        problemDetail.setType(URI.create(
                "https://api.universidade.com/erros/erro-interno"
        ));

        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

}