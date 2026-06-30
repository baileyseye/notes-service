package baileyseyes.notesservice.service;

import baileyseyes.notesservice.request.NoteRequest;
import baileyseyes.notesservice.model.Note;
import java.util.List;

public interface NoteService {

    List<Note> getAllNotes();

    List<Note> getNotesByTag(String tag);

    Note getNoteById(Long id);

    Note createNote(NoteRequest request);

    Note updateNote(Long id, NoteRequest request);

    boolean deleteNote(Long id);
}
