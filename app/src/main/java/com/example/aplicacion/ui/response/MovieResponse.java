package com.example.aplicacion.ui.response;

import com.example.aplicacion.ui.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Esta clase solo sirve para obtener 1 película en concreto
public class MovieResponse {


    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
