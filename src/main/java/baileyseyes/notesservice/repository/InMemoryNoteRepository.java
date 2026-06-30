package baileyseyes.notesservice.repository;

import baileyseyes.notesservice.model.Note;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryNoteRepository implements NoteRepository {

    private final Map<Long, Note> notes = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Note save(Note note) {
        if (note.getId() == null) {
            note.setId(idGenerator.incrementAndGet());
        }

        notes.put(note.getId(), note);

        return note;
    }

    @Override
    public Optional<Note> findById(Long id) {
        return Optional.ofNullable(notes.get(id));
    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(notes.values());
    }

    @Override
    public List<Note> findByTag(String tag) {
        List<Note> result = new ArrayList<>();

        for (Note note : notes.values()) {
            if (note.getTags() != null && note.getTags().contains(tag)) {
                result.add(note);
            }
        }

        return result;
    }

    @Override
    public boolean deleteById(Long id) {
        return notes.remove(id) != null;
    }
}
