package baileyseyes.notesservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import baileyseyes.notesservice.exception.NoteNotFoundException;
import baileyseyes.notesservice.exception.NoteValidationException;
import baileyseyes.notesservice.model.Note;
import baileyseyes.notesservice.repository.NoteRepository;
import baileyseyes.notesservice.request.NoteRequest;
import baileyseyes.notesservice.service.impl.NoteServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тесты для {@linkplain NoteService заметок}
 */
@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    private NoteService noteService;

    private Note testNote;

    @BeforeEach
    void setUp() {
        testNote = new Note(1L,
            "Test Note", "Test Content",
            List.of("work", "urgent"), null);

        noteService = new NoteServiceImpl(noteRepository);
    }

    @Test
    @DisplayName("Создание заметки с валидными данными")
    void createNoteShouldReturnCreatedNote() {
        NoteRequest request = new NoteRequest();
        request.setTitle("Новая заметка");
        request.setContent("Содержимое заметки");
        request.setTags(List.of("личное"));

        Note savedNote = new Note(1L, "Новая заметка",
            "Содержимое заметки", List.of("личное"), null);

        when(noteRepository.save(any(Note.class)))
            .thenReturn(savedNote);

        Note result = noteService.createNote(request);

        assertAll("Проверка созданной заметки",
            () -> assertEquals("Новая заметка", result.getTitle(), "Заголовок должен совпадать"),
            () -> assertEquals("Содержимое заметки", result.getContent(), "Содержимое должно совпадать"),
            () -> assertEquals(List.of("личное"), result.getTags(), "Теги должны совпадать")
        );

        verify(noteRepository).save(any(Note.class));
    }

    @Test
    @DisplayName("Создание заметки с пустым заголовком выбрасывает исключение")
    void createNoteShouldThrowWhenTitleBlank() {
        NoteRequest request = new NoteRequest();
        request.setTitle("");
        request.setContent("Содержимое");

        assertThrows(NoteValidationException.class,
            () -> noteService.createNote(request), "Должно выбрасываться исключение при пустом заголовке");
    }

    @Test
    @DisplayName("Получение несуществующей заметки выбрасывает исключение")
    void getNoteByIdShouldThrowWhenNotFound() {
        when(noteRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class,
            () -> noteService.getNoteById(99L), "Должно выбрасываться исключение при отсутствии заметки");
    }

    @Test
    @DisplayName("Обновление существующей заметки")
    void updateNoteShouldUpdateExistingNote() {
        NoteRequest request = new NoteRequest();
        request.setTitle("Обновлённый заголовок");
        request.setContent("Обновлённое содержимое");

        Note updatedNote = new Note(1L,
            "Обновлённый заголовок", "Обновлённое содержимое",
            List.of("работа", "срочно"), null);

        when(noteRepository.findById(1L))
            .thenReturn(Optional.of(testNote));

        when(noteRepository.save(any(Note.class)))
            .thenReturn(updatedNote);

        Note result = noteService.updateNote(1L, request);

        assertAll("Проверка обновлённой заметки",
            () -> assertEquals("Обновлённый заголовок", result.getTitle(), "Заголовок должен обновиться"),
            () -> assertEquals("Обновлённое содержимое", result.getContent(), "Содержимое должно обновиться")
        );

        verify(noteRepository).save(any(Note.class));
    }

    @Test
    @DisplayName("Удаление существующей заметки возвращает true")
    void deleteNoteShouldReturnTrueWhenDeleted() {
        when(noteRepository.deleteById(1L))
            .thenReturn(true);

        boolean result = noteService.deleteNote(1L);

        assertTrue(result, "Удаление должно вернуть true");
    }

    @Test
    @DisplayName("Удаление несуществующей заметки возвращает false")
    void deleteNoteShouldReturnFalseWhenNotFound() {
        when(noteRepository.deleteById(99L))
            .thenReturn(false);

        boolean result = noteService.deleteNote(99L);

        assertFalse(result, "Удаление несуществующей заметки должно вернуть false");
    }
}
