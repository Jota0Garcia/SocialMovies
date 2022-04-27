package com.example.aplicacion.ui.repositories;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.request.MovieApiClient;
import com.google.android.gms.common.internal.LibraryVersion;

import java.util.List;

public class MovieRepository {


    private static MovieRepository instancia;

    private MovieApiClient movieApiClient;




    public static MovieRepository getInstance(){
        if (instancia==null){
            instancia=new MovieRepository();
        }
        return instancia;
    };

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){return movieApiClient.getMovies();}


    // llamamos al metodo en el repo
    public void searchMovieApi(String query, int pageNumber ){
        movieApiClient.searchMoviesApi(query,pageNumber);
    }


}
