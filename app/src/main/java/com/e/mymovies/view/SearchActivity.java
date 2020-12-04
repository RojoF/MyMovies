package com.e.mymovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.e.mymovie.R;
import com.e.mymovie.databinding.ActivitySearchBinding;


public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE ="" ;
    private ActivitySearchBinding binding;
    public String word = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.searchButton.setOnClickListener(v -> addSearch());


    }

    private void addSearch() {

        if (!binding.searchBox.getText().toString().isEmpty()){
        Intent intent = new Intent(this, MovieActivity.class);
        word = String.valueOf(binding.searchBox.getText());
        intent.putExtra(EXTRA_MESSAGE, word);
        binding.searchBox.setText("");
        startActivity(intent);
    }
        else {
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    getResources().getText(R.string.validate_text), Toast.LENGTH_SHORT);
            toast1.show();
        }

    }
}