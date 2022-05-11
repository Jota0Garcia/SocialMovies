package com.example.aplicacion.ui.utils;


import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    //Búsqueda de películas por KEYWORD en el título
    //https://api.themoviedb.org/3/movie/550?api_key=0b05799dd0167d9b2c1eab45c812921f&query=Star+Wars
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //Busqueda por peliculas populares
    //https://api.themoviedb.org/3/movie/popular ?api_key=52a18783ed514602a5facb15a0177e61&page=1
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );






    // Búsqueda de películas por Id específico
    // Como es 1 sola se parametriza con la clase MovieModel, que es para un unico objeto.
    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
        @Path("movie_id") int movie_id,
        @Query("api_key") String api_key
    );

}