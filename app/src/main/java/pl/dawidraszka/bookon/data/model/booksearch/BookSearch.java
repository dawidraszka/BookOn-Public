package pl.dawidraszka.bookon.data.model.booksearch;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "booksearch_table")
public class BookSearch {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String isbn;

    public BookSearch(@Nullable String title, @Nullable String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getValue(Parameter parameter) {
        return parameter == Parameter.ISBN ? isbn : title;
    }

    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        if (title != null) {
            parameters.add(Parameter.TITLE);
        }
        if (isbn != null) {
            parameters.add(Parameter.ISBN);
        }
        return parameters;
    }
}
