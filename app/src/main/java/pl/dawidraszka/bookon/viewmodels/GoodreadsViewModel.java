package pl.dawidraszka.bookon.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.data.model.booksearch.Parameter;
import pl.dawidraszka.bookon.data.repository.booksearch.BookSearchRepository;

public class GoodreadsViewModel extends ViewModel {
    private BookSearchRepository bookSearchRepository;
    private LiveData<Parameter> currentParameter;

    public void init(Context context) {
        bookSearchRepository = BookSearchRepository.getInstance(context);
        currentParameter = bookSearchRepository.getCurrentParameter();
    }

    public LiveData<Parameter> getCurrentParameter() {
        return currentParameter;
    }

    public BookSearch getBookSearch() {
        return bookSearchRepository.getBookSearch();
    }
}
