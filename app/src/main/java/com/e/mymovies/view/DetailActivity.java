package com.e.mymovies.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.e.mymovies.R;
import com.e.mymovies.domain.Movie;
import com.e.mymovies.net.OnGetMovieCall;
import com.e.mymovies.net.WebService;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.e.mymovies.view.MovieActivity.MOVIE_ID;


public class DetailActivity extends AppCompatActivity {

    private PhotoView photo;
    private TextView title;
    private ImageButton share;
    private TextView description;
    private CardView card;
    private TextView releaseDate;
    private TextView rating;
    private static final int IMAGE_SIDE_PX = 250;
    private String url = "http://image.tmdb.org/t/p/w780";
    private WebService moviesRepository = null;
    private int movieId;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        movieId = getIntent().getIntExtra(String.valueOf(MOVIE_ID), movieId);
        moviesRepository = WebService.getInstance();
        initUI();
        getMovie();
        card = findViewById(R.id.card);
        share.setOnClickListener(v -> {
            try {
                comparteView(v = card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void getMovie() {
        moviesRepository.getMovie(movieId, new OnGetMovieCall() {
            @Override
            public void onSuccess(Movie movie) {
                title.setText(movie.getTitle());
                description.setText(movie.getOverview());
                rating.setText(String.valueOf(movie.getRating()));

                Picasso.get()
                        .load(url + movie.getBackdrop())
                        .resize(IMAGE_SIDE_PX, IMAGE_SIDE_PX)
                        .centerCrop()
                        .into(photo);

            }

            @Override
            public void onError() {
                Toast.makeText(DetailActivity.this,
                        getResources().getText(R.string.error), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initUI() {
        photo = findViewById(R.id.poster_movie);
        photo.isZoomable();
        releaseDate = findViewById(R.id.release_date);
        title = findViewById(R.id.title_movie);
        description = findViewById(R.id.description_movie);
        rating = findViewById(R.id.rating_movie);
        share = findViewById(R.id.button_share);
    }


    private void comparteView(View v) throws IOException {
        // Creamos un bitmap con el tamaño de la vista
        Bitmap bitmap = Bitmap.createBitmap(
                v.getWidth(),
                v.getHeight(), Bitmap.Config.ARGB_8888
        );
        // Creamos el canvas para pintar en el bitmap
        Canvas canvas = new Canvas(bitmap);
        // Pintamos el contenido de la vista en el canvas y así en el bitmap
        v.draw(canvas);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Uri uriF = null;
        try {
            File f = File.createTempFile("sharedImage", ".jpg",
                    getExternalCacheDir());
            f.deleteOnExit();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(stream.toByteArray());
            fo.close();

            uriF = FileProvider.getUriForFile(this, this.getApplicationContext()
                    .getPackageName() + ".provider", f);


            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/jpeg");

            sharingIntent.putExtra(Intent.EXTRA_STREAM, uriF);
            startActivity(
                    Intent.createChooser(
                            sharingIntent, getResources().getText(R.string.send)
                    )
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}







