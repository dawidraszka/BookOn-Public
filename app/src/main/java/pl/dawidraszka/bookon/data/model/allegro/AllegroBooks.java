package pl.dawidraszka.bookon.data.model.allegro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;

public class AllegroBooks {
    private BookSearch bookSearch;
    private String sortType;
    private List<AllegroBook> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllegroBooks that = (AllegroBooks) o;
        return Objects.equals(bookSearch, that.bookSearch) &&
                Objects.equals(sortType, that.sortType) &&
                Objects.equals(books, that.books);
    }

    public BookSearch getBookSearch() {
        return bookSearch;
    }

    public void setBookSearch(BookSearch bookSearch) {
        this.bookSearch = bookSearch;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<AllegroBook> getBooks() {
        return books;
    }

    public void setBooks(List<AllegroBook> books) {
        this.books = books;
    }
}
