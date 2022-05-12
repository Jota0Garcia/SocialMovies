package com.example.aplicacion.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.repositories.MovieRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MovieRepository movieRepository;
    public DashboardViewModel(){
        movieRepository=MovieRepository.getInstance();
    }
    public DashboardViewModel(MutableLiveData<List<MovieModel>> mMovies) {
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