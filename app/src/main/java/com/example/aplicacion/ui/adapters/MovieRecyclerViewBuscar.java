package com.example.aplicacion.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicacion.R;
import com.example.aplicacion.ui.models.MovieModel;

import java.util.List;

public class MovieRecyclerViewBuscar  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieModel> mMoviesBuscar;
    private OnMovieListener onMovieListenerBuscar;

    public MovieRecyclerViewBuscar(OnMovieListener onMovieListenerBuscar) {
        this.onMovieListenerBuscar = onMovieListenerBuscar;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item_buscar
                ,parent,false);
        return new MovieViewHolderBuscar(view, onMovieListenerBuscar);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        ((MovieViewHolderBuscar)holder).titleBuscar.setText(mMoviesBuscar.get(i).getTitle());

        ((MovieViewHolderBuscar)holder).release_dateBuscar.setText(mMoviesBuscar.get(i).getRelease_date());
///*
        ((MovieViewHolderBuscar)holder).durationBuscar.setText(mMoviesBuscar.get(i).getOriginal_language());

        //Nuestro rating es sobre 5 y el de la api sobre 10 por eso el /2
        ((MovieViewHolderBuscar)holder).ratingBarBuscar.setRating((mMoviesBuscar.get(i).getVote_average())/2);
        //ImageView usando Glide Library
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        + mMoviesBuscar.get(i).getPoster_path())
                .into((((MovieViewHolderBuscar)holder).imageViewBuscar));

        // */
    }

    @Override
    public int getItemCount() {
        if(mMoviesBuscar != null){
            return mMoviesBuscar.size();
        }
        return 0;
    }

    public void setmMoviesBuscar(List<MovieModel> mMoviesBuscar) {
        this.mMoviesBuscar = mMoviesBuscar;
        notifyDataSetChanged();
    }
}
