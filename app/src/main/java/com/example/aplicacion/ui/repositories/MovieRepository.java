package com.example.aplicacion.ui.repositories;

import androidx.lifecycle.LiveData;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.request.MovieApiClient;

import java.util.List;

public class MovieRepository {


    private static MovieRepository instancia;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;



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

    public LiveData<List<MovieModel>> getPop(){return movieApiClient.getMoviesPop();}


    // llamamos al metodo en el repo
    public void searchMovieApi(String query, int pageNumber ){
        mQuery= query;
        mPageNumber= pageNumber;
        movieApiClient.searchMoviesApi(query,pageNumber);

    }

    public void searchMoviePop(int pageNumber ){
        mPageNumber= pageNumber;
        movieApiClient.searchMoviesPop(pageNumber);
    }

    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1);
    }

    public void searchNextPagePop(){
        searchMoviePop(mPageNumber+1);
    }

    public int getmPageNumber(){
        return mPageNumber;
    }


}
