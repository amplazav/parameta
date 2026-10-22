package parameta.demo.parameta.config;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import parameta.demo.parameta.dto.ResponseApi;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApi<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(err -> (err instanceof FieldError fieldError
                    ? fieldError.getField()
                    : err.getObjectName()) + ": " + err.getDefaultMessage())
            .collect(Collectors.toList());

        return ResponseEntity.badRequest()
            .body(new ResponseApi<>("VALIDATION_ERROR", "Error de validación", errors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseApi<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(409)
                .body(new ResponseApi<>("CONFLICT", "El registro ya existe o infringe una relación de datos.", null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseApi<Object>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        String errorMessage = "Error en el formato del cuerpo de la petición.";

        // Si es por formato de fecha mal escrita
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            if (cause.getTargetType().equals(LocalDate.class)) {
                errorMessage = "La fecha debe tener el formato 'yyyy-MM-dd'.";
            }
        }

        return ResponseEntity.badRequest()
            .body(new ResponseApi<>("FORMAT_ERROR", errorMessage, null));
    }
}
