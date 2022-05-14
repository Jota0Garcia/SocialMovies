package com.example.aplicacion.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.MovieDetails;
import com.example.aplicacion.R;
import com.example.aplicacion.ui.adapters.MovieRecyclerView;
import com.example.aplicacion.ui.adapters.OnMovieListener;
import com.example.aplicacion.ui.models.MovieModel;

public class DashboardFragment extends Fragment implements OnMovieListener {

    private DashboardViewModel movieListViewModelBuscar;
    private RecyclerView recyclerViewBuscar;
    private MovieRecyclerView movieRecyclerViewAdapterBuscar;
    private SearchView searchViewBuscar;
    private Toolbar toolbar;
    boolean isPopular = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard,container,false);

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        searchViewBuscar = (SearchView) root.findViewById(R.id.search_view);
        SetupSearchView();

        recyclerViewBuscar = (RecyclerView) root.findViewById(R.id.recyclerViewNew);

        movieListViewModelBuscar = new ViewModelProvider(this).get(DashboardViewModel.class);
        ConfigureRecyclerView();
        ObserveAnyChange();
        //ObservePopularMovies();

        return root;
    }

    private void ObservePopularMovies() {
        movieListViewModelBuscar.getPop().observe(getViewLifecycleOwner(), movieModels -> {
            if (movieModels != null) {
                for (MovieModel movieModel : movieModels) {
                    //Log.v("Tag", "Han cambiado: " + movieModel.getTitle());
                    //recyclerViewBuscar.clearOnScrollListeners();
                    movieRecyclerViewAdapterBuscar.setmMovies(movieModels);
                }
            }
        });
    }

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
                    //Log.v("Tag","Han cambiado: "+movieModel.getTitle());
                    //recyclerViewBuscar.clearOnScrollListeners();
                    movieRecyclerViewAdapterBuscar.setmMovies(movieModels);
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
        movieRecyclerViewAdapterBuscar = new MovieRecyclerView(this);
        recyclerViewBuscar.setAdapter(movieRecyclerViewAdapterBuscar);
        //No deja poner this tiene que ser clase context entonces puse getContext() o requireContext()
        recyclerViewBuscar.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //recyclerViewBuscar.setLayoutManager(new LinearLayoutManager(getContext()));
        //Recycler view Pagination
        // Loading next page of api response
        recyclerViewBuscar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollHorizontally(1)){
                    // Aqui mostramos los proximos resultados para la proxima pagina
                    movieListViewModelBuscar.searchNextPage();
                }
            }
        }
        );

    }

    @Override
    public void onMovieClick(int position) {
        //Toast.makeText(getContext(),"The position"+position,Toast.LENGTH_SHORT).show();

        //no necesitamos la posicion
        //necesitamos el id para obtener todos los detalles

        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapterBuscar.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

}