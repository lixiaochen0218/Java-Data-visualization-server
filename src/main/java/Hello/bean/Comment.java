package Hello.bean;

/**
 * Created by Alex on 2017/8/13.
 */
public class Comment {
    private String author;
    private String text;
    private String date;

    public Comment(String author, String text, String date) {
        this.author = author;
        this.text = text;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
