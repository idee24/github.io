package momatechsoftwares.rest_demo.networking.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable{

    private String overview;
    private String title;

    @SerializedName("vote_average")
    private double ratings;

    public Movie(String title, double ratings, String releaseDate) {
        this.title = title;
        this.ratings = ratings;
        this.releaseDate = releaseDate;
    }

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("posterPath")
    private String posterPath;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "overview='" + overview + '\'' +
                ", title='" + title + '\'' +
                ", ratings=" + ratings +
                ", releaseDate='" + releaseDate + '\'' +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}
