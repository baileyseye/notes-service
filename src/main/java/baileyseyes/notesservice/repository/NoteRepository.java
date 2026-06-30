package baileyseyes.notesservice.repository;

import baileyseyes.notesservice.model.Note;
import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    Note save(Note note);

    Optional<Note> findById(Long id);

    List<Note> findAll();

    List<Note> findByTag(String tag);

    boolean deleteById(Long id);
}
