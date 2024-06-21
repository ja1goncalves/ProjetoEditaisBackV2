package br.com.vitrine.edital.exception;

import br.com.vitrine.edital.model.dto.ResponseExceptionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerExceptionEdital {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ResponseExceptionDTO> UsuarioException(UsuarioException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getResponseError(request, HttpStatus.UNPROCESSABLE_ENTITY, ex));

    }

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ResponseExceptionDTO> NaoEncontradoException(NaoEncontradoException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getResponseError(request, HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler(RegistroExistenteException.class)
    public ResponseEntity<ResponseExceptionDTO> RegistroExistenteException(RegistroExistenteException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(getResponseError(request, HttpStatus.CONFLICT, ex));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseExceptionDTO> handleException(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getResponseError(request, HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    private ResponseExceptionDTO getResponseError(HttpServletRequest request, HttpStatus httpStatus, Exception e) {
        return ResponseExceptionDTO.builder()
                .statusCode(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();

    }
}

