package pl.dawidraszka.bookon.data.model.booksearch;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BookSearch.class}, version = 1, exportSchema = false)
public abstract class BookSearchDatabase extends RoomDatabase {
    private static BookSearchDatabase instance;

    public static synchronized BookSearchDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BookSearchDatabase.class, "booksearch_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract BookSearchDao bookSearchDao();
}
