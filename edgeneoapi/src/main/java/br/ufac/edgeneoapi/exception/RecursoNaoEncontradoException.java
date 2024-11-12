package br.ufac.edgeneoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Retornar status 409 (Conflito
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }

    public RecursoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
