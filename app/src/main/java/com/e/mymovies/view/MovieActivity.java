package com.e.mymovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.e.mymovie.R;
import com.e.mymovie.databinding.ActivityMainBinding;
import com.e.mymovie.domain.Movie;
import com.e.mymovie.net.GetMoviesCall;
import com.e.mymovie.net.WebService;
import com.e.mymovie.ui.MovieAdapter;
import com.e.mymovie.ui.MovieClick;

import java.util.List;

public class MovieActivity extends AppCompatActivity {

    public static final int MOVIE_ID = 0;
    private ActivityMainBinding binding;
    private MovieAdapter adapter= null;
    private WebService moviesRepository = null;
    private String QUERY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Intent intent = getIntent();
        QUERY = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);
        this.setTitle(QUERY);

        binding.moviesList.setLayoutManager(new LinearLayoutManager(this));

        moviesRepository = WebService.getInstance();

        binding.moviesList.setLayoutManager(new LinearLayoutManager(this));

        moviesRepository.getMovies(new GetMoviesCall() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MovieAdapter(movies, callback);
                binding.moviesList.setAdapter(adapter);
            }

            @Override
            public void onError() {
                Toast.makeText(MovieActivity.this,
                        getResources().getText(R.string.error), Toast.LENGTH_SHORT).show();
            }


        }, QUERY);

    }

    MovieClick callback = movie -> {
        Intent intent = new Intent(MovieActivity.this, DetailActivity.class);
        intent.putExtra(String.valueOf(MovieActivity.MOVIE_ID), movie.getId());
        startActivity(intent);
        //Toast toast1 = Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG);
        //toast1.show();
    };
}

