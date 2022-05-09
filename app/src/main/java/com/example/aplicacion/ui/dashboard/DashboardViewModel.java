package com.example.aplicacion.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.repositories.MovieRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    //private final MutableLiveData<String> mText;

    //public DashboardViewModel() {
    //    mText = new MutableLiveData<>();
    //    mText.setValue("This is dashboard fragment");
    //}

    //public LiveData<String> getText() {
    //    return mText;
    //}
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

    // Llamamos los metodos en el viewmodel.
    public void searchMovieApi(String query, int pageNumber)
    {
        movieRepository.searchMovieApi(query,pageNumber);
    }

    public void searchNextPage() {
        movieRepository.searchNextPage();

    }
}