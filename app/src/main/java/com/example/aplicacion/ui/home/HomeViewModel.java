package com.example.aplicacion.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.repositories.MovieRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MovieRepository movieRepository;
    public HomeViewModel(){
        movieRepository=MovieRepository.getInstance();
    }
    public HomeViewModel(MutableLiveData<List<MovieModel>> mMovies) {
        movieRepository=MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }
    // Llamamos los metodos en el viewmodel.
    public void searchMovieApi(String query, int pageNumber)
    {
        movieRepository.searchMovieApi(query,pageNumber);
    }

    public void searchMoviePop(int pageNumber) {
        movieRepository.searchMoviePop(pageNumber);
    }
    public void searchNextPage() {
        movieRepository.searchNextPage();

    }
}