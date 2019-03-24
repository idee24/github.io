package momatechsoftwares.rest_demo.networking.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import momatechsoftwares.rest_demo.networking.models.Movie;

public class AuthResponse implements Serializable{

    public AuthResponse(List<Movie> movies) {
        this.movies = movies;
    }

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movie) {
        this.movies = movie;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "movie=" + movies +
                '}';
    }
}
