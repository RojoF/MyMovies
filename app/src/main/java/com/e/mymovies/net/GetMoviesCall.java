package com.e.mymovies.net;





import com.e.mymovies.domain.Movie;

import java.util.List;

public interface GetMoviesCall {


    void onSuccess(List<Movie> movies);

    void onError();

}
