package com.e.mymovies.net;


import com.e.mymovies.domain.Movie;

public interface OnGetMovieCall {

    void onSuccess(Movie movie);

    void onError();
}
