package com.example.aplicacion.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.MovieDetails;
import com.example.aplicacion.R;
import com.example.aplicacion.databinding.FragmentHomeBinding;
import com.example.aplicacion.ui.adapters.MovieRecyclerView;
import com.example.aplicacion.ui.adapters.OnMovieListener;
import com.example.aplicacion.ui.models.MovieModel;

public class HomeFragment extends Fragment implements OnMovieListener {

    private FragmentHomeBinding binding;
    private HomeViewModel movieListViewModel;
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerViewAdapter;
    boolean isPopular = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        movieListViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();
        //Para coger los datos de las peliculas populares
        movieListViewModel.searchMoviePop(1);
        return root;
    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(getViewLifecycleOwner(), movieModels -> {
            if (movieModels != null) {
                for (MovieModel movieModel : movieModels) {
                    //Log.v("Tag", "Han cambiado: " + movieModel.getTitle());
                    recyclerView.clearOnScrollListeners();
                    movieRecyclerViewAdapter.setmMovies(movieModels);
                }
            }
        });
    }

    // Func para detectar cambios
    private void ObserveAnyChange(){
        //No puedo poner this me obliga a poner getViewLifecycleOwner()
        movieListViewModel.getMovies().observe(getViewLifecycleOwner(), movieModels -> {
            if(movieModels != null){
                for(MovieModel movieModel: movieModels){
                    Log.v("Tag","Han cambiado: "+movieModel.getTitle());
                    recyclerView.clearOnScrollListeners();
                    movieRecyclerViewAdapter.setmMovies(movieModels);
                }
            }

        });
    }


    // Llamada al metodo en nuestra pagina principal

    private void searchMovieApi(String query,int pageNumber){
        movieListViewModel.searchMovieApi(query,pageNumber);
    }
    //Inicializar recyclerView y añadirle datos
    private void ConfigureRecyclerView(){
        //Live data cant be passed via the constructor
        movieRecyclerViewAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        //No deja poner this tiene que ser clase context entonces puse getContext() o requireContext()
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    // Aqui mostramos los proximos resultados para la proxima pagina
                    movieListViewModel.searchNextPage();
                }
            }
        }
        );
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }
}