package baileyseyes.notesservice.exception;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

import java.util.Map;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@SuppressWarnings("unused")
@Order(HIGHEST_PRECEDENCE)
public class NoteControllerAdvice {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoteNotFound(NoteNotFoundException ex) {
        return status(NOT_FOUND)
            .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(NoteValidationException.class)
    public ResponseEntity<Map<String, String>> handleNoteValidation(NoteValidationException ex) {
        return status(BAD_REQUEST)
            .body(Map.of("error", ex.getMessage()));
    }
}
