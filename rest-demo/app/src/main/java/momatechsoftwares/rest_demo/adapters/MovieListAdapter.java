package momatechsoftwares.rest_demo.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.activities.MovieListActivity;
import momatechsoftwares.rest_demo.fragments.MovieOverviewFragment;
import momatechsoftwares.rest_demo.networking.response.AuthResponse;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private LayoutInflater inflater;
    private AuthResponse movieList;
    private MovieListActivity movieListActivity;
    private FragmentManager manager;


    public MovieListAdapter(MovieListActivity movieListActivity, AuthResponse movieList) {
        this.inflater = LayoutInflater.from(movieListActivity);
        this.movieList = movieList;
        this.movieListActivity = movieListActivity;
    }


    static class MovieViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTextView;
        private TextView releaseDateTextView;
        private TextView ratingsTextView;

        MovieViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.movieTitleTextView);
            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
            ratingsTextView = itemView.findViewById(R.id.ratingsTextView);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        String ratings = String.valueOf(movieList.getMovies().get(position).getRatings());
        holder.titleTextView.setText(movieList.getMovies().get(position).getTitle());
        holder.releaseDateTextView.setText(movieList.getMovies().get(position).getReleaseDate());
        holder.ratingsTextView.setText(ratings);
        final int positionSnapshot = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager = movieListActivity.getSupportFragmentManager();
                movieListActivity.setSelectedMovie(movieList.getMovies().get(positionSnapshot));
                MovieOverviewFragment movieOverviewFragment = new MovieOverviewFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.container, movieOverviewFragment, "MovieOverview");
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.getMovies().size();
    }
}
