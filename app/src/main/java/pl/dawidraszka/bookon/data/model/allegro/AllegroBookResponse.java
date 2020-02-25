package pl.dawidraszka.bookon.data.model.allegro;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AllegroBookResponse {
    private List<AllegroBook> promotedBooks = new ArrayList<>();
    private List<AllegroBook> regularBooks = new ArrayList<>();

    public AllegroBookResponse(JsonObject jsonObject) {
        JsonArray promotedBooksJson = jsonObject.getAsJsonObject("items").getAsJsonArray("promoted");
        JsonArray regularBooksJson = jsonObject.getAsJsonObject("items").getAsJsonArray("regular");

        for (int i = 0; i < promotedBooksJson.size(); i++) {
            AllegroBook allegroBook = new AllegroBook(promotedBooksJson.get(i).getAsJsonObject());
            promotedBooks.add(allegroBook);
        }

        for (int i = 0; i < regularBooksJson.size(); i++) {
            AllegroBook allegroBook = new AllegroBook(regularBooksJson.get(i).getAsJsonObject());
            regularBooks.add(allegroBook);
        }
    }

    public List<AllegroBook> getBooks() {
        List<AllegroBook> allBooks = new ArrayList<>();
        allBooks.addAll(promotedBooks);
        allBooks.addAll(regularBooks);
        return allBooks;
    }
}
