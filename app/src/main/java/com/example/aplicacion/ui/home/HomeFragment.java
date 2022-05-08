package com.example.aplicacion.ui.home;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieListViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        searchMovieApi("Fast", 1);
        return root;
    }

    // Func para detectar cambios
    private void ObserveAnyChange(){
        //No puedo poner this me obliga a poner getViewLifecycleOwner()
        movieListViewModel.getMovies().observe(getViewLifecycleOwner(), movieModels -> {
            if(movieModels != null){
                for(MovieModel movieModel: movieModels){
                    Log.v("Tag","Han cambiado: "+movieModel.getTitle());

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMovieClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

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
}