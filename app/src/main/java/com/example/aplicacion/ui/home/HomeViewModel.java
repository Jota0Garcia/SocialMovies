package com.example.aplicacion.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.repositories.MovieRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    //private final MutableLiveData<String> mText;

    //public HomeViewModel() {
    //    mText = new MutableLiveData<>();
    //    mText.setValue("This is home fragment");
    //}


    //public LiveData<String> getText() {return mText;}
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

    // Llamamos los metodos en el viewmodel.
    public void searchMovieApi(String query, int pageNumber)
    {
        movieRepository.searchMovieApi(query,pageNumber);
    }
}