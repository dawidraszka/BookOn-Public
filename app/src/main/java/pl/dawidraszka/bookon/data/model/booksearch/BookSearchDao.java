package pl.dawidraszka.bookon.data.model.booksearch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookSearchDao {

    @Insert
    void insert(BookSearch bookSearch);

    @Update
    void update(BookSearch bookSearch);

    @Delete
    void delete(BookSearch bookSearch);

    @Query("SELECT * FROM booksearch_table")
    LiveData<List<BookSearch>> getSavedBookSearches();
}
