package com.brasil.api.com.brasil.api.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder{
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requisição inválida!");
            case 404 -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado!");
            case 500 -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no servidor externo!");
            default -> new Exception("Erro inesperado: " + response.status());
        };
    }
}
