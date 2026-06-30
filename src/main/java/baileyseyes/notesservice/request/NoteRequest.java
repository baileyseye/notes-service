package baileyseyes.notesservice.request;

import java.util.List;

public class NoteRequest {

    /** Заголовок заметки */
    private String title;

    /** Содержимое заметки */
    private String content;

    /** Теги заметки */
    private List<String> tags;

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
}
