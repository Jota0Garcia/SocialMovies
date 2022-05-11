package com.example.aplicacion.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.MovieDetails;
import com.example.aplicacion.R;
import com.example.aplicacion.ui.adapters.MovieRecyclerViewBuscar;
import com.example.aplicacion.ui.adapters.OnMovieListener;
import com.example.aplicacion.ui.models.MovieModel;

public class DashboardFragment extends Fragment implements OnMovieListener {

    private DashboardViewModel movieListViewModelBuscar;
    private RecyclerView recyclerViewBuscar;
    private MovieRecyclerViewBuscar movieRecyclerViewAdapterBuscar;
    private SearchView searchViewBuscar;
    private Toolbar toolbar;
    boolean isPopular = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //DashboardViewModel dashboardViewModel =
        //        new ViewModelProvider(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard,container,false);
        //View root = binding.getRoot();


        //final RecyclerView recyclerView = binding.recyclerViewNew;
        //(dashboardViewModel).observe(getViewLifecycleOwner(),recyclerView::setAdapter);
        //final TextView textView = binding.textDashboard;
       //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        //searchview
        searchViewBuscar = (SearchView) root.findViewById(R.id.search_view);
        SetupSearchView();

        recyclerViewBuscar = (RecyclerView) root.findViewById(R.id.recyclerViewNew);
        movieListViewModelBuscar = new ViewModelProvider(this).get(DashboardViewModel.class);
        ConfigureRecyclerView();
        ObserveAnyChange();
        //searchMovieApi("Fast", 1);


        return root;
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //View root = binding.getRoot();
        //final RecyclerView recyclerView = binding.recyclerViewNew;
        //(dashboardViewModel).observe(getViewLifecycleOwner(),recyclerView::setAdapter);
        //final TextView textView = binding.textDashboard;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        //recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewNew);
        recyclerView = binding.recyclerViewNew;
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieListViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        searchMovieApi("fast", 1);

    }

 */
    private void SetupSearchView() {
        searchViewBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModelBuscar.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchViewBuscar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPopular=false;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        com.example.aplicacion.databinding.FragmentDashboardBinding binding = null;
    }
    // Func para detectar cambios
    private void ObserveAnyChange(){
        //No puedo poner this me obliga a poner getViewLifecycleOwner()
        movieListViewModelBuscar.getMovies().observe(getViewLifecycleOwner(), movieModels -> {
            if(movieModels != null){
                for(MovieModel movieModel: movieModels){
                    Log.v("Tag","Han cambiado: "+movieModel.getTitle());

                    movieRecyclerViewAdapterBuscar.setmMoviesBuscar(movieModels);
                }
            }

        });
    }


    // Llamada al metodo en nuestra pagina principal

    //private void searchMovieApi(String query,int pageNumber){
    //    movieListViewModel.searchMovieApi(query,pageNumber);
    //}
    //Inicializar recyclerView y añadirle datos
    private void ConfigureRecyclerView(){
        //Live data cant be passed via the constructor
        movieRecyclerViewAdapterBuscar = new MovieRecyclerViewBuscar(this);
        recyclerViewBuscar.setAdapter(movieRecyclerViewAdapterBuscar);
        //No deja poner this tiene que ser clase context entonces puse getContext() o requireContext()
        recyclerViewBuscar.setLayoutManager(new LinearLayoutManager(getContext()));
        //Recycler view Pagination
        // Loading next page of api response
        recyclerViewBuscar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    // Aqui mostramos los proximos resultados para la proxima pagina
                    movieListViewModelBuscar.searchNextPage();
                }
            }
        }
        );








    }

    @Override
    public void onMovieClick(int position) {
        Toast.makeText(getContext(),"The position"+position,Toast.LENGTH_SHORT).show();

        //no necesitamos la posicion
        //necesitamos el id para obtener todos los detalles

        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapterBuscar.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }
/*
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
    */
}