package baileyseyes.notesservice.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import baileyseyes.notesservice.model.Note;
import baileyseyes.notesservice.request.NoteRequest;
import baileyseyes.notesservice.service.NoteService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер {@linkplain Note заметок}
 */
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * GET {@code http://localhost:8080/notes?tag=work}
     * <br><br>
     * Query params:
     * <ul>
     *   <li>tag - тег для фильтрации</li>
     * </ul>
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false) String tag) {
        List<Note> notes = tag != null
            ? noteService.getNotesByTag(tag)
            : noteService.getAllNotes();

        return ok(notes);
    }

    /**
     * POST {@code http://localhost:8080/notes}
     * <br><br>
     * Body:
     * <pre>{@code
     * {
     *   "title": "Заголовок",
     *   "content": "Содержимое",
     *   "tags": ["work", "urgent"]
     * }
     * }</pre>
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody NoteRequest request) {
        Note note = noteService.createNote(request);

        return status(CREATED).body(note);
    }

    /**
     * GET {@code http://localhost:8080/notes/{id}}
     * <br>
     * Пример: {@code http://localhost:8080/notes/1}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);

        return ok(note);
    }

    /**
     * PUT {@code http://localhost:8080/notes/{id}}
     * <br><br>
     * Body:
     * <pre>{@code
     * {
     *   "title": "Новый заголовок",
     *   "content": "Новое содержимое",
     *   "tags": ["work"]
     * }
     * }</pre>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody NoteRequest request) {
        Note note = noteService.updateNote(id, request);

        return ok(note);
    }

    /**
     * DELETE {@code http://localhost:8080/notes/{id}}
     * <br>
     * Пример: {@code http://localhost:8080/notes/1}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        boolean isDeleted = noteService.deleteNote(id);

        return isDeleted
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
