package pl.dawidraszka.bookon.data.remote.allegro;

import com.google.gson.JsonElement;

import pl.dawidraszka.bookon.credentials.AllegroCredentials;
import pl.dawidraszka.bookon.data.model.allegro.AccessToken;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AllegroBookAPI {

    String API_BASE_URL = "https://api.allegro.pl";

    @Headers({"Authorization: Basic " + AllegroCredentials.CREDENTIALS_BASE64})
    @POST("https://allegro.pl/auth/oauth/token?grant_type=client_credentials")
    Call<AccessToken> getOAuth2Token();

    @Headers({"Accept: application/vnd.allegro.public.v1+json"})
    @GET("offers/listing?category.id=7&sellingMode.format=BUY_NOW&limit=100")
    Call<JsonElement> getBookListings(@Query("phrase") String phrase, @Query("sort") String sort);
}
