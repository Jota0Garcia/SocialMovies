package com.example.aplicacion.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicacion.R;
import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP=1;
    private static final int DISPLAY_SEARCH=2;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item
                    ,parent,false);
            return new MovieViewHolder(view,onMovieListener);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout
                    ,parent,false);
            return new Popular_View_Holder(view,onMovieListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        int itemViewType = getItemViewType(i);
        if(itemViewType==DISPLAY_SEARCH){
            //Nuestro rating es sobre 5 y el de la api sobre 10 por eso el /2
            ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(i).getVote_average())/2);
            //ImageView usando Glide Library
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"
                            +mMovies.get(i).getPoster_path())
                    .into((((MovieViewHolder)holder).imageView));
        }else{
            //Nuestro rating es sobre 5 y el de la api sobre 10 por eso el /2
            ((Popular_View_Holder)holder).ratingBar22.setRating((mMovies.get(i).getVote_average())/2);
            //ImageView usando Glide Library
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"
                            +mMovies.get(i).getPoster_path())
                    .into((((Popular_View_Holder)holder).imageView22));
        }



        // */
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
       return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }



    //obtener id de la pelicula pulsada

    public MovieModel getSelectedMovie(int position){
        if( mMovies != null){
            if(mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR){
            return DISPLAY_POP;
        }else{
            return DISPLAY_SEARCH;
        }
    }
}
