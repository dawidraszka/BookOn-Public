package pl.dawidraszka.bookon.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.data.repository.booksearch.BookSearchRepository;

public class BookSearchViewModel extends ViewModel {
    private BookSearchRepository bookSearchRepository;

    public void init(@NonNull Context context) {
        bookSearchRepository = BookSearchRepository.getInstance(context);
    }

    public void setBookSearch(BookSearch bookSearch) {
        bookSearchRepository.setBookSearch(bookSearch);
    }

    public void saveBookSearch(BookSearch bookSearch) {
        bookSearchRepository.saveBookSearch(bookSearch);
    }

    public BookSearch getBookSearch() {
        return bookSearchRepository.getBookSearch();
    }
}
