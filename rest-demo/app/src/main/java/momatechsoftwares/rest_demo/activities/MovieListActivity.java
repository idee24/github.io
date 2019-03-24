package momatechsoftwares.rest_demo.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;

import java.util.LinkedList;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.adapters.MovieListAdapter;
import momatechsoftwares.rest_demo.fragments.MovieOverviewFragment;
import momatechsoftwares.rest_demo.networking.Routes;
import momatechsoftwares.rest_demo.networking.generators.ServiceGenerator;
import momatechsoftwares.rest_demo.networking.models.Movie;
import momatechsoftwares.rest_demo.networking.response.AuthResponse;
import momatechsoftwares.rest_demo.networking.services.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = MovieListActivity.class.getSimpleName();
    private FrameLayout container;
    private RecyclerView moviesRecyclerView;
    private View parentGroup;
    private Movie selectedMovie;
    private DrawerLayout drawerLayout;

    public MovieListActivity (){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        moviesRecyclerView = findViewById(R.id.movies_recycler_view);
        container = findViewById(R.id.container);
        parentGroup = findViewById(android.R.id.content);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moviesRecyclerView.setLayoutManager(layoutManager);
        runMovies();

        if (getSupportActionBar() != null){

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (container.getVisibility() == View.VISIBLE ){

            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            MovieOverviewFragment movieOverviewFragment = new MovieOverviewFragment();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(movieOverviewFragment)
                    .remove(movieOverviewFragment)
                    .detach(movieOverviewFragment)
                    .commit();
                    container.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.dashboard:
                Intent dashboardIntent = new Intent(MovieListActivity.this, DashboardActivity.class);
                startActivity(dashboardIntent);
                finish();
                break;

            case R.id.timetable:
                Intent intent = new Intent(MovieListActivity.this, CardOfCardsActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.attendance:
                Intent customIntent = new Intent(MovieListActivity.this, CustomActivity.class);
                startActivity(customIntent);
                finish();
                break;

            case R.id.comments:
                Intent commentsIntent = new Intent(MovieListActivity.this, CommentsActivity.class);
                startActivity(commentsIntent);
                finish();
                break;

            case R.id.assessments:
                ShowSnack("Send");
                break;

            case R.id.fireBase:
                Intent fireBaseIntent = new Intent
                        (MovieListActivity.this, FireBaseActivity.class);
                startActivity(fireBaseIntent);
                finish();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void runMovies(){

        MovieService service = ServiceGenerator.createService(MovieService.class);
        Call<AuthResponse> call = service.getTopRatedMovies(Routes.API_KEY);
        Log.i(TAG, call.toString());
        call.enqueue(new Callback<AuthResponse>() {

            @Override
            public void onResponse
                    (@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {

                if (response.body() != null){

                    AuthResponse movies = response.body();
                    refreshMovieRecyclerView(movies);
                }
                else {
                    failedToLoad();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                failedToLoad();
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                 break;

            case R.id.discard:
                ShowSnack("Discard");
                break;

            case R.id.edit:
                ShowSnack("Edit");
                break;

            case R.id.settings:
                ShowSnack("Settings");
                break;

            case R.id.exit:
                ShowSnack("Exit");
                break;

            case R.id.search:
                ShowSnack("Search");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ShowSnack(String msg){
        Snackbar.make(parentGroup, msg + " Failed", Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Try Again Later", Toast.LENGTH_SHORT).show();
            }
        }).setActionTextColor(Color.RED).show();
    }

    public void refreshMovieRecyclerView(AuthResponse movieList){
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movieList);
        moviesRecyclerView.setAdapter(movieListAdapter);
    }

    private void failedToLoad(){

        LinkedList<Movie> movieList = new LinkedList<>();
        movieList.add(new Movie("Fight Club", 5.0, "24-August-2018"));
        AuthResponse authResponse = new AuthResponse(movieList);
        refreshMovieRecyclerView(authResponse);
    }


    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

}
