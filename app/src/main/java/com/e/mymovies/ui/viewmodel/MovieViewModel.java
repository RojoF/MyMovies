package com.e.mymovies.ui.viewmodel;

/*public class MovieViewModel extends AndroidViewModel {
    private MutableLiveData<List<Movie>> movies;
    public LiveData<List<Movie>> loadMovies() {
        if (movies == null) {
            movies = new MutableLiveData<List<Movie>>();
            getMovies();
        }
        return movies;
    }

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private WebService movieRepository = new WebService();
    MovieActivity activity = new MovieActivity();
    private boolean isConnected;
    Movie movie = new Movie();
    MovieAdapter movieAdapter = new MovieAdapter(movie,activity.Onclick(movie));

    public MovieViewModel(@NonNull Application application) {
        super(application);

    }

    public void getMovies() {
//
        isConnected = Connection.isNetworkAvailable(getApplication());
        if (isConnected) {

            Map<String, String> map = new HashMap<>();
            map.put("api_key", "e248f066d7f6197a407676509fd7a589");
            movieRepository.getMovies(map);

        }
    }*/
