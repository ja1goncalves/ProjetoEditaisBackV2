package br.com.vitrine.edital.exception;

public class UsuarioException extends RuntimeException {

    public UsuarioException() {
    }

    public UsuarioException(String message) {
        super(message);
    }
}
