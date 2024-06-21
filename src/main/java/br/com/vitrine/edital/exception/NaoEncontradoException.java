package br.com.vitrine.edital.exception;

public class NaoEncontradoException extends RuntimeException {

    public NaoEncontradoException() {
    }

    public NaoEncontradoException(String message) {
        super(message);
    }
}
