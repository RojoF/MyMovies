package com.e.mymovies.net;

import androidx.annotation.NonNull;


import com.e.mymovies.data.MoviesWrapperResponse;
import com.e.mymovies.domain.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "e248f066d7f6197a407676509fd7a589";
    private static final String LANGUAGE = "es-ES";
    private static WebService repository;
    private ApiService api;

    public WebService(ApiService api) {
        this.api = api;
    }

    public static WebService getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new WebService(retrofit.create(ApiService.class));
        }

        return repository;
    }

    public void getMovies(final GetMoviesCall callback, String QUERY) {
        api.getSearchMovies(API_KEY, LANGUAGE, 1,QUERY)
                .enqueue(new Callback<MoviesWrapperResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesWrapperResponse> call, @NonNull
                            Response<MoviesWrapperResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesWrapperResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesWrapperResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
    public void getMovie(int movieId, final OnGetMovieCall callback) {
        api.getMovie(movieId, API_KEY, LANGUAGE)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}

