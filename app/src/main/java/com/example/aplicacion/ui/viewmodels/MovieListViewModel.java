package com.example.aplicacion.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    // Clase para el viewmodel

    private MovieRepository movieRepository;


    public MovieListViewModel(MutableLiveData<List<MovieModel>> mMovies) {
        movieRepository=MovieRepository.getInstance();
    }


    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    // Llamamos los metodos en el viewmodel.
    public void searchMovieApi(String query, int pageNumber)
    {
        movieRepository.searchMovieApi(query,pageNumber);
    }
}
