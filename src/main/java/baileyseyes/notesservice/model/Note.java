package baileyseyes.notesservice.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель заметки.
 */
@SuppressWarnings("unused")
public class Note {

    /** Идентификатор заметки */
    private Long id;

    /** Заголовок заметки */
    private String title;

    /** Содержимое заметки */
    private String content;

    /** Теги заметки */
    private List<String> tags = new ArrayList<>();

    /** Время создания заметки */
    private LocalDateTime createdAt;

    public Note() {
    }

    public Note(Long id, String title, String content, List<String> tags, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
