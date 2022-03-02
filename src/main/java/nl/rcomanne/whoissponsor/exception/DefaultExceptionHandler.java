package nl.rcomanne.whoissponsor.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    public static final String MSG = "msg";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(final Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(Map.of(MSG, "internal server error"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(Map.of(MSG, ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(final NoSuchElementException ex) {
        log.warn("NoSuchElementException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(MSG, ex.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(final ResponseStatusException ex) {
        log.warn("ResponseStatusException: {}", ex.getMessage(), ex);
        final var body = new HashMap<String, String>();
        if (Strings.isNotBlank(ex.getReason())) {
            body.put(MSG, ex.getReason());
        } else {
            body.put(MSG, ex.getStatus().getReasonPhrase());
        }
        return ResponseEntity
                .status(ex.getStatus())
                .headers(ex.getResponseHeaders())
                .body(body);
    }
}
