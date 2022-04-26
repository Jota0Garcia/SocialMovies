package com.example.aplicacion.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.MovieListActivity;
import com.example.aplicacion.R;
import com.example.aplicacion.databinding.FragmentHomeBinding;
import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.request.Servicey;
import com.example.aplicacion.ui.response.MovieSearchResponse;
import com.example.aplicacion.ui.utils.Credentials;
import com.example.aplicacion.ui.utils.MovieApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button btn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    //private MovieListViewModel movieListViewModel;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Forma de buscar pulsando el boton
        //View view = inflater.inflate(R.layout.fragment_home, container, false);
        btn = (Button) view.findViewById(R.id.buttonBuscar);
        //View v = getView().findViewById(R.id.buttonBuscar);
        //btn = (Button) getActivity().findViewById(R.id.buttonBuscar);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.i("1","Prueba API Pulsada");
                GetRetrofitResponse();
            }
        });
        /*
        //Forma de buscar con el searchView
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        
        //movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //ConfigureRecyclerView();
        //ObserveAnyChange();
        //searchView
        SearchView searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        pageNumber:1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //SetupSearchView();
        */

    }
    //private void SetupSearchView() {
    //    SearchView searchView = (SearchView) view.findViewById(R.id.buttonBuscar);
    //}


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void GetRetrofitResponse() {
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(Credentials.API_KEY, "Action", 1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("Tag", "the response" + response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for (MovieModel movie : movies) {
                        Log.v("Tag", "The List" + movie.getTitle());
                    }
                } else {
                    try {
                        Log.v("Tag", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
}