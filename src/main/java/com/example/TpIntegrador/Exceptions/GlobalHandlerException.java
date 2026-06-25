package com.example.TpIntegrador.Exceptions;

import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueCargaActivaException;
import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueDuplicatedException;
import com.example.TpIntegrador.Exceptions.BuqueExceptions.BuqueNotFoundException;
import com.example.TpIntegrador.Exceptions.ClienteExceptions.ClienteNotFoundException;
import com.example.TpIntegrador.Exceptions.ClienteExceptions.DniDuplicatedException;
import com.example.TpIntegrador.Exceptions.ContenedroExceptions.ContenedorNotFoundException;
import com.example.TpIntegrador.Exceptions.ContenedroExceptions.EspacioInsufcienteException;
import com.example.TpIntegrador.Exceptions.ContenedroExceptions.NotVipException;
import com.example.TpIntegrador.Exceptions.PuertoExceptions.PuertoNotFoundException;
import com.example.TpIntegrador.Exceptions.Response.ErrorResponse;
import com.example.TpIntegrador.Exceptions.PuertoExceptions.PuertoDuplicatedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {



    @ExceptionHandler(EspacioInsufcienteException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerEspacioInsuficiente(EspacioInsufcienteException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value()) // 409
                .error(HttpStatus.CONFLICT.getReasonPhrase()) // "Peticion correcta, no pasa la regla del negocio"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotVipException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerNotVip(NotVipException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value()) // 404
                .error(HttpStatus.CONFLICT.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BuqueCargaActivaException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerBuqueCarga(BuqueCargaActivaException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value()) // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContenedorNotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerbContenedorNotFound(ContenedorNotFoundException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value()) // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BuqueNotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerbBuqueNotFound(BuqueNotFoundException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value()) // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BuqueDuplicatedException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerbBuqueDuplicated(BuqueDuplicatedException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value()) // 404
                .error(HttpStatus.CONFLICT.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(PuertoNotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerbPuertoNotFound(PuertoNotFoundException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value()) // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PuertoDuplicatedException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerbPuertoDuplicated(PuertoDuplicatedException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value()) //
                .error(HttpStatus.CONFLICT.getReasonPhrase()) //
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotActiveException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerbNotActiveException(NotActiveException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value()) // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerClienteNotFound(ClienteNotFoundException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value()) // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DniDuplicatedException.class)
    public ResponseEntity<@NonNull ErrorResponse> handlerDniDuplicatedException(DniDuplicatedException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value()) //
                .error(HttpStatus.CONFLICT.getReasonPhrase()) //
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)

    //Uso map porque la llave seria el nombre del campo y el valor es el mensaje que ponemos en el dto
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Recorremos todos los errores que saltaron en la lisata de errores usando un for each, extraemos en nombre del campo y despues el mensaje de defini en el dto
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje); // aca se hace el json
        });

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
