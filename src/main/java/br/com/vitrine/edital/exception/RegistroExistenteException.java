package br.com.vitrine.edital.exception;

public class RegistroExistenteException extends RuntimeException {

    public RegistroExistenteException() {
    }

    public RegistroExistenteException(String message) {
        super(message);
    }
}
