package pl.dawidraszka.bookon.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pl.dawidraszka.bookon.data.model.allegro.AllegroBook;
import pl.dawidraszka.bookon.data.model.booksearch.Parameter;
import pl.dawidraszka.bookon.data.repository.allegro.AllegroBookRepository;
import pl.dawidraszka.bookon.data.repository.booksearch.BookSearchRepository;

public class AllegroViewModel extends ViewModel {

    private AllegroBookRepository allegroBookRepository;
    private BookSearchRepository bookSearchRepository;

    private LiveData<Parameter> currentParameter;
    private LiveData<List<AllegroBook>> currentBooks;

    public void init(Context context) {
        allegroBookRepository = AllegroBookRepository.getInstance();
        bookSearchRepository = BookSearchRepository.getInstance(context);

        currentParameter = bookSearchRepository.getCurrentParameter();
        currentBooks = allegroBookRepository.getBooks();
    }

    public LiveData<Parameter> getCurrentParameter() {
        return bookSearchRepository.getCurrentParameter();
    }

    public LiveData<List<AllegroBook>> getCurrentBooks() {
        return currentBooks;
    }

    public void changeBooks(String sortType) {
        if (currentParameter.getValue() == Parameter.ISBN) {
            allegroBookRepository.getIsbnBooks(bookSearchRepository.getBookSearch(), sortType);
        } else {
            allegroBookRepository.getTitleBooks(bookSearchRepository.getBookSearch(), sortType);
        }
    }
}
