package com.example.aplicacion.ui.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aplicacion.AppExecutors;
import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.response.MovieSearchResponse;
import com.example.aplicacion.ui.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    // Esto es el livedata
    private MutableLiveData<List<MovieModel>> mMovies;// Clase necesaria para el viewmodel
    private static MovieApiClient instancia;
    // Cremos un "Runable" GLOBAL.
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    // Esto es el livedata para popular
    private MutableLiveData<List<MovieModel>> mMoviesPop;// Clase necesaria para el viewmodel
    // Cremos un "Runable" para popular.
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;


    public static MovieApiClient getInstance(){
        if(instancia==null){
            instancia=new MovieApiClient();
        }

        return instancia;
    }

    private MovieApiClient(){
        mMovies=new MutableLiveData<>();
        mMoviesPop=new MutableLiveData<>();
    }



    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPop;
    }


    // ESTE METODO SE LLAMARÁ ENTRE LAS DIFERENTES CLASES PARA CONSUMIR LA API.
    public void searchMoviesApi(String query,int pageNumber){

        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;

        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query,pageNumber);



        final Future myHandler=AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);


                AppExecutors.getInstance().networkIO().schedule(new Runnable() {
                    @Override
                    public void run() { // Aquí cancelamos la solicitud a la api según el tiempo de espera
                        myHandler.cancel(true); // Cancela el handler
                    }
                }, 30, TimeUnit.SECONDS); // Añadimos un timeout a la llamada de la api.

    }

    //consumir api para popular
    public void searchMoviesPop(int pageNumber){

        if(retrieveMoviesRunnablePop != null){
            retrieveMoviesRunnablePop = null;

        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);



        final Future myHandler2=AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);


        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() { // Aquí cancelamos la solicitud a la api según el tiempo de espera
                myHandler2.cancel(true); // Cancela el handler
            }
        }, 30, TimeUnit.SECONDS); // Añadimos un timeout a la llamada de la api.

    }

    // Obtención de datos de RestAPI con la siguiente clase ejecutable
    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        // El constructor de la clase
        public RetrieveMoviesRunnable(String query, int pageNumber){
            this.query=query;
            this.pageNumber=pageNumber;
            cancelRequest=false;
        }

        @Override
        public void run() { // El método que otiene los objetos de la respuesta
            try{
                Response response=getMovies(query,pageNumber).execute();
                Log.v("El response","El code es: "+response.code());
                if(cancelRequest){
                    return;
                }
                if(response.code()==200){ // Si la consumición de la api ha ido bien, guardamos las películas en la lista.
                    List<MovieModel> list=new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    Log.v("Peliculas","Las peliculas son: "+response.code());
                    if(pageNumber==1){
                        // Tendremos que enviar los datos al "Live Data"

                        mMovies.postValue(list); // Este método tardará un poco en publicar los datos en nuestro LiveData pero lo hará de manera asíncrona, por lo que no importa desde el hilo que se llame.
                    } else{

                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);

                    }


                } else{ // Si no hay conexión o el response code no es 200 lanzamos error.

                    String error =response.errorBody().string();
                    Log.v("Tag","Error"+error);
                    mMovies.postValue(null);

                }



            } catch (IOException e){
                e.printStackTrace();
                mMovies.postValue(null);
            }


            if(cancelRequest){
                Log.v("Tag","La búsqueda se ha cancelado");
                return; // Si se ha cancelado la solicitud no hacer nada.
            }
        }


        // Método para la búsqueda con query. Este método se encarga de llamar
        // Similar a esta funcion:   Call<MovieSearchResponse> responseCall=movieApi.searchMovie(Credentials.API_KEY,"Jack Reacher",1);
        // Pero permite parámetros personalizados
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Servicey.getMovieApi().searchMovie(Credentials.API_KEY,query,pageNumber);
        }


        private void cancelRequest(){
            Log.v("Tag","La búsqueda se ha cancelado");
            cancelRequest=true;
        }
    }

    // Obtención de datos de RestAPI con la siguiente clase ejecutable para popular
    private class RetrieveMoviesRunnablePop implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        // El constructor de la clase
        public RetrieveMoviesRunnablePop(int pageNumber){
            this.pageNumber=pageNumber;
            cancelRequest=false;
        }

        @Override
        public void run() { // El método que otiene los objetos de la respuesta
            try{
                Response response2=getPop(pageNumber).execute();
                Log.v("El response","El code es: "+response2.code());
                if(cancelRequest){
                    return;
                }
                if(response2.code()==200){ // Si la consumición de la api ha ido bien, guardamos las películas en la lista.
                    List<MovieModel> list=new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    Log.v("Peliculas","Las peliculas son: "+response2.code());
                    if(pageNumber==1){
                        // Tendremos que enviar los datos al "Live Data"

                        mMoviesPop.postValue(list); // Este método tardará un poco en publicar los datos en nuestro LiveData pero lo hará de manera asíncrona, por lo que no importa desde el hilo que se llame.
                    } else{

                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);

                    }


                } else{ // Si no hay conexión o el response code no es 200 lanzamos error.

                    String error =response2.errorBody().string();
                    Log.v("Tag","Error"+error);
                    mMoviesPop.postValue(null);

                }



            } catch (IOException e){
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }


            if(cancelRequest){
                Log.v("Tag","La búsqueda se ha cancelado");
                return; // Si se ha cancelado la solicitud no hacer nada.
            }
        }


        // Método para la búsqueda con query. Este método se encarga de llamar
        // Similar a esta funcion:   Call<MovieSearchResponse> responseCall=movieApi.searchMovie(Credentials.API_KEY,"Jack Reacher",1);
        // Pero permite parámetros personalizados
        private Call<MovieSearchResponse> getPop(int pageNumber){
            return Servicey.getMovieApi().getPopular(Credentials.API_KEY,pageNumber);
        }


        private void cancelRequest(){
            Log.v("Tag","La búsqueda se ha cancelado");
            cancelRequest=true;
        }
    }


}
