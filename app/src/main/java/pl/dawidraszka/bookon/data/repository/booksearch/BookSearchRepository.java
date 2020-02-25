package pl.dawidraszka.bookon.data.repository.booksearch;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.data.model.booksearch.BookSearchDao;
import pl.dawidraszka.bookon.data.model.booksearch.BookSearchDatabase;
import pl.dawidraszka.bookon.data.model.booksearch.Parameter;

public class BookSearchRepository {

    private static BookSearchRepository instance;
    private BookSearch bookSearch;
    private MutableLiveData<Parameter> currentParameter;

    private BookSearchDao bookSearchDao;
    private LiveData<List<BookSearch>> savedBookSearches;

    private BookSearchRepository() {
    }

    public static BookSearchRepository getInstance(Context context) {
        if (instance == null) {
            instance = new BookSearchRepository();
            BookSearchDatabase bookSearchDatabase = BookSearchDatabase.getInstance(context);
            instance.bookSearchDao = bookSearchDatabase.bookSearchDao();
            instance.savedBookSearches = instance.bookSearchDao.getSavedBookSearches();

            instance.currentParameter = new MutableLiveData<>();
        }
        return instance;
    }

    public BookSearch getBookSearch() {
        return bookSearch;
    }

    public void setBookSearch(BookSearch bookSearch) {
        this.bookSearch = bookSearch;
        if(bookSearch.getTitle() != null){
            currentParameter.setValue(Parameter.TITLE);
        }
        if(bookSearch.getIsbn() != null){
            currentParameter.setValue(Parameter.ISBN);
        }
    }

    public LiveData<Parameter> getCurrentParameter() {
        return currentParameter;
    }

    public void setCurrentParameter(Parameter currentParameter) {
        this.currentParameter.setValue(currentParameter);
    }

    public LiveData<List<BookSearch>> getSavedBookSearches() {
        return savedBookSearches;
    }

    public void saveBookSearch(BookSearch bookSearch) {
        new SaveBookSearchAsyncTask(bookSearchDao).execute(bookSearch);
    }

    public void deleteBookSearch(BookSearch bookSearch) {
        new DeleteBookSearchAsyncTask(bookSearchDao).execute(bookSearch);
    }

    public void updateBookSearch(BookSearch bookSearch) {
        new UpdateBookSearchAsyncTask(bookSearchDao).execute(bookSearch);
    }

    private static class SaveBookSearchAsyncTask extends AsyncTask<BookSearch, Void, Void> {
        private BookSearchDao bookSearchDao;

        private SaveBookSearchAsyncTask(BookSearchDao bookSearchDao) {
            this.bookSearchDao = bookSearchDao;
        }

        @Override
        protected Void doInBackground(BookSearch... bookSearch) {
            bookSearchDao.insert(bookSearch[0]);
            return null;
        }
    }

    private static class DeleteBookSearchAsyncTask extends AsyncTask<BookSearch, Void, Void> {
        private BookSearchDao bookSearchDao;

        private DeleteBookSearchAsyncTask(BookSearchDao bookSearchDao) {
            this.bookSearchDao = bookSearchDao;
        }

        @Override
        protected Void doInBackground(BookSearch... bookSearch) {
            bookSearchDao.delete(bookSearch[0]);
            return null;
        }
    }

    private static class UpdateBookSearchAsyncTask extends AsyncTask<BookSearch, Void, Void> {
        private BookSearchDao bookSearchDao;

        private UpdateBookSearchAsyncTask(BookSearchDao bookSearchDao) {
            this.bookSearchDao = bookSearchDao;
        }

        @Override
        protected Void doInBackground(BookSearch... bookSearch) {
            bookSearchDao.update(bookSearch[0]);
            return null;
        }
    }
}
