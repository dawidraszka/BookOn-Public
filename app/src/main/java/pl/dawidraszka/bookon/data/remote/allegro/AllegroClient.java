package pl.dawidraszka.bookon.data.remote.allegro;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllegroClient {

    public static AllegroBookAPI getClient() {
        OkHttpClient client = new OkHttpClient.Builder().authenticator(new TokenAuthenticator()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AllegroBookAPI.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AllegroBookAPI.class);
    }
}
