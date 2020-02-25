package pl.dawidraszka.bookon.ui.savedsearches;

import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;

public interface EditSavedSearchDialogListener {
    void onSuccessfulEdit(BookSearch bookSearch);
}