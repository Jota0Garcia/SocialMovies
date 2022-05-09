package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aplicacion.ui.models.MovieModel;

public class MovieDetails extends AppCompatActivity {

    //widgets
    private ImageView imageViewDetails;
    private TextView titleDetails, descDetails;
    private RatingBar ratingBarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);



        imageViewDetails = findViewById(R.id.imageViewdetails);
        titleDetails= findViewById(R.id.textViewtitledetails);
        descDetails=findViewById(R.id.textViewdesc_details);
        ratingBarDetails= findViewById(R.id.ratingBar);

        GetDataFromIntent();

    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            Log.v("tagy", "incoming intent" + movieModel.getMovie_id());
        }

    }
}