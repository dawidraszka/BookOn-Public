package pl.dawidraszka.bookon.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.data.repository.booksearch.BookSearchRepository;

public class SavedSearchesViewModel extends ViewModel {
    private BookSearchRepository bookSearchRepository;

    public void init(@NonNull Context context) {
        bookSearchRepository = BookSearchRepository.getInstance(context);
    }

    public LiveData<List<BookSearch>> getSavedSearches() {
        return bookSearchRepository.getSavedBookSearches();
    }

    public void delete(BookSearch bookSearch) {
        bookSearchRepository.deleteBookSearch(bookSearch);
    }

    public void update(BookSearch bookSearch) {
        bookSearchRepository.updateBookSearch(bookSearch);
    }

    public void setBookSearch(BookSearch bookSearch) {
        bookSearchRepository.setBookSearch(bookSearch);
    }
}
