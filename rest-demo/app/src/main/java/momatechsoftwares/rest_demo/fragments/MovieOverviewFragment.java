package momatechsoftwares.rest_demo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.activities.MovieListActivity;

public class MovieOverviewFragment extends Fragment {

    View currentView;

    public MovieOverviewFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_overview, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MovieListActivity movieListActivity = (MovieListActivity) getActivity();
        assert movieListActivity != null;

        currentView = getView();
        if (currentView == null){
            return;
        }
        TextView descriptionTextView = currentView.findViewById(R.id.movie_description_text_view);
        TextView titleDescription = currentView.findViewById(R.id.titleDescription);

        if (movieListActivity.getSelectedMovie() != null){

            descriptionTextView.setText(movieListActivity.getSelectedMovie().getOverview());
            titleDescription.setText(movieListActivity.getSelectedMovie().getTitle());
        }
        }
    }
