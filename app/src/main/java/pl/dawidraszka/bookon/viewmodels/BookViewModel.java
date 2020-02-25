package pl.dawidraszka.bookon.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.data.model.booksearch.Parameter;
import pl.dawidraszka.bookon.data.repository.booksearch.BookSearchRepository;

public class BookViewModel extends ViewModel {
    private BookSearchRepository bookSearchRepository;

    public void init(Context context) {
        bookSearchRepository = BookSearchRepository.getInstance(context);
    }

    public BookSearch getBookSearch() {
        return bookSearchRepository.getBookSearch();
    }

    public void setCurrentParameter(Parameter parameter) {
        bookSearchRepository.setCurrentParameter(parameter);
    }
}
