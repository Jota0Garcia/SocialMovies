package com.example.aplicacion.ui.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.aplicacion.ui.utils.Credentials;
import com.example.aplicacion.ui.utils.MovieApi;


public class Servicey {

    private static Retrofit.Builder retrofitBuilder=
            new Retrofit.Builder().baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit=retrofitBuilder.build();

    private static MovieApi movieApi=retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }

}
