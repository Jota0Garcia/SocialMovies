package com.example.aplicacion;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.request.Servicey;
import com.example.aplicacion.ui.response.MovieSearchResponse;
import com.example.aplicacion.ui.utils.Credentials;
import com.example.aplicacion.ui.utils.MovieApi;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {


    // ESTA CLASE ES CAPAZ DE ENCONTRAR UNA LISTA DE PELÍCULAS DADO CIERTA KEYWORD EN EL TÍTULO
    // LA KEYWORD ESTÁ METIDA EN LA LÍNEA Call<MovieSearchResponse> responseCal............



    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.buttonTest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test","Prueba pulsacion API");
                GetRetrofitResponse();
            }
        });


    }

    private void GetRetrofitResponse() {
        MovieApi movieApi= Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall=movieApi.searchMovie(Credentials.API_KEY,"Jack Reacher",1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()==200){
                    Log.v("Tag","the response"+response.body().toString());
                    List<MovieModel> movies=new ArrayList<>(response.body().getMovies());
                    for (MovieModel movie : movies){
                        Log.v("Tag","The List"+movie.getRelease_date());
                    }
                }
                else{
                    try{
                        Log.v("Tag","Error"+response.errorBody().string());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });

    }


    private void GetRetrofitResponseById(){
        MovieApi api= Servicey.getMovieApi();
        Call<MovieModel> responseCall= api.getMovie(550,
                Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code()==200){
                    MovieModel movie = response.body();
                    Log.v("Tag", "La película obtenida es: "+movie.getTitle());
                }else{
                    try{
                        Log.v("Tag","Error"+response.errorBody().string());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }




}
