package baileyseyes.notesservice.service.impl;

import baileyseyes.notesservice.request.NoteRequest;
import baileyseyes.notesservice.exception.NoteNotFoundException;
import baileyseyes.notesservice.exception.NoteValidationException;
import baileyseyes.notesservice.model.Note;
import baileyseyes.notesservice.repository.NoteRepository;
import baileyseyes.notesservice.service.NoteService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getNotesByTag(String tag) {
        return noteRepository.findByTag(tag);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
            .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @Override
    public Note createNote(NoteRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new NoteValidationException("Заголовок не должен быть пустым");
        }

        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setTags(request.getTags());
        note.setCreatedAt(LocalDateTime.now());

        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long id, NoteRequest request) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new NoteNotFoundException(id));

        Optional.ofNullable(request.getTitle()).ifPresent(note::setTitle);
        Optional.ofNullable(request.getContent()).ifPresent(note::setContent);
        Optional.ofNullable(request.getTags()).ifPresent(note::setTags);

        return noteRepository.save(note);
    }

    @Override
    public boolean deleteNote(Long id) {
        return noteRepository.deleteById(id);
    }
}
