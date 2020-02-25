package pl.dawidraszka.bookon.data.remote.allegro;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import pl.dawidraszka.bookon.data.model.allegro.AccessToken;
import pl.dawidraszka.bookon.data.repository.allegro.AllegroBookRepository;


public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) {

        AccessToken accessToken = AllegroBookRepository.getAccessToken();
        return response.request().newBuilder()
                .header("Authorization", "Bearer " + accessToken.getAccessToken())
                .build();
    }
}