package br.com.vitrine.edital.exception;

public class DadoInvalidoException extends RuntimeException {

    public DadoInvalidoException() {
    }

    public DadoInvalidoException(String message) {
        super(message);
    }
}
