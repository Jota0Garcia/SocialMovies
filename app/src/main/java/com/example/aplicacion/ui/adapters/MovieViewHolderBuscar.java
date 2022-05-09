package com.example.aplicacion.ui.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.R;

public class MovieViewHolderBuscar extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView titleBuscar, release_dateBuscar, durationBuscar;
    ImageView imageViewBuscar;
    RatingBar ratingBarBuscar;
    CardView cardViewBuscar;

    //Click listener
    OnMovieListener onMovieListenerBuscar;

    public MovieViewHolderBuscar(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListenerBuscar = onMovieListener;
        cardViewBuscar=itemView.findViewById(R.id.card_viewBuscar);
        titleBuscar =itemView.findViewById(R.id.movie_titleBuscar);
        release_dateBuscar = itemView.findViewById(R.id.movie_categoryBuscar);
        durationBuscar = itemView.findViewById(R.id.movie_durationBuscar);

        imageViewBuscar = itemView.findViewById(R.id.movie_imgBuscar);
        ratingBarBuscar = itemView.findViewById(R.id.rating_barBuscar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onMovieListenerBuscar.onMovieClick(getAdapterPosition());
    }
}
