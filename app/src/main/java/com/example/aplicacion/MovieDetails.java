package com.example.aplicacion;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aplicacion.ui.models.MovieModel;

public class MovieDetails extends AppCompatActivity  {

    private MovieModel movieModel;

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
        ratingBarDetails= findViewById(R.id.ratingBar_details);

        GetDataFromIntent();

    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")){
            movieModel = getIntent().getParcelableExtra("movie");
            //Log.v("tagy", "incoming intent " + movieModel.getTitle());
            titleDetails.setText(movieModel.getTitle());
            descDetails.setText(movieModel.getMovie_overview());
            ratingBarDetails.setRating(movieModel.getVote_average()/2);
            Log.v("tagy", " " + movieModel.getMovie_overview());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"
                            +movieModel.getPoster_path())
                    .into(imageViewDetails);




        }

    }

}