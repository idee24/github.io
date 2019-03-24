package momatechsoftwares.rest_demo.networking.services;

import android.support.annotation.NonNull;

import java.util.List;

import momatechsoftwares.rest_demo.networking.Routes;
import momatechsoftwares.rest_demo.networking.models.Movie;
import momatechsoftwares.rest_demo.networking.response.AuthResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @NonNull
    @GET(Routes.TOP_RATED_ENDPOINT)
    Call<AuthResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
