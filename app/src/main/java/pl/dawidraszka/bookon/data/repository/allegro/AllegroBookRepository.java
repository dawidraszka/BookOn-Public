package pl.dawidraszka.bookon.data.repository.allegro;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.dawidraszka.bookon.data.model.allegro.AccessToken;
import pl.dawidraszka.bookon.data.model.allegro.AllegroBook;
import pl.dawidraszka.bookon.data.model.allegro.AllegroBookResponse;
import pl.dawidraszka.bookon.data.model.allegro.AllegroBooks;
import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.data.remote.allegro.AllegroBookAPI;
import pl.dawidraszka.bookon.data.remote.allegro.AllegroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllegroBookRepository {

    private static final AllegroBookRepository instance = new AllegroBookRepository();
    private static AllegroBookAPI allegroBookClient = AllegroClient.getClient();

    private MutableLiveData<List<AllegroBook>> currentBooks = new MutableLiveData<>(new ArrayList<>());

    private AllegroBooks allegroTitleBooks = new AllegroBooks();
    private AllegroBooks allegroIsbnBooks = new AllegroBooks();

    private AllegroBookRepository() {
    }

    public static AllegroBookRepository getInstance() {
        return instance;
    }

    public static AccessToken getAccessToken() {
        Call<AccessToken> call = allegroBookClient.getOAuth2Token();
        AccessToken accessToken = null;
        try {
            accessToken = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public MutableLiveData<List<AllegroBook>> getBooks() {
        return currentBooks;
    }

    public void getTitleBooks(BookSearch bookSearch, String sortType) {
        if (allegroTitleBooks.getBookSearch() == bookSearch && allegroTitleBooks.getSortType().equals(sortType)) {
            currentBooks.setValue(allegroTitleBooks.getBooks());
        } else {
            allegroTitleBooks.setBookSearch(bookSearch);
            allegroTitleBooks.setSortType(sortType);

            Call<JsonElement> call = allegroBookClient.getBookListings(bookSearch.getTitle(), sortType);
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    AllegroBookResponse allegroBookResponse = new AllegroBookResponse(response.body().getAsJsonObject());
                    allegroTitleBooks.setBooks(allegroBookResponse.getBooks());
                    currentBooks.setValue(allegroTitleBooks.getBooks());
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                }
            });
        }
    }

    public void getIsbnBooks(BookSearch bookSearch, String sortType) {
        if (allegroIsbnBooks.getBookSearch() == bookSearch && allegroIsbnBooks.getSortType().equals(sortType)) {
            currentBooks.setValue(allegroIsbnBooks.getBooks());
        } else {
            allegroIsbnBooks.setBookSearch(bookSearch);
            allegroIsbnBooks.setSortType(sortType);

            Call<JsonElement> call = allegroBookClient.getBookListings(bookSearch.getIsbn(), sortType);
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    AllegroBookResponse allegroBookResponse = new AllegroBookResponse(response.body().getAsJsonObject());
                    allegroIsbnBooks.setBooks(allegroBookResponse.getBooks());
                    currentBooks.setValue(allegroIsbnBooks.getBooks());
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                }
            });
        }
    }
}
