package com.example.aplicacion.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicacion.MainActivity;
import com.example.aplicacion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NotificationsFragment extends Fragment {


    ImageView fotoPerfilT;
    ImageView fotoPerfil;
    EditText nombreT, apellidosT, edadT;
    EditText nombre, apellidos, edad;
    TextView correoT;
    TextView correo;
    Button datos, cerrar;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_notifications,container,false);



        fotoPerfilT = (ImageView) root.findViewById(R.id.imageView2);
        nombreT = (EditText) root.findViewById(R.id.nombreView);
        apellidosT = (EditText) root.findViewById(R.id.apellidosView);
        edadT = (EditText) root.findViewById(R.id.edadView);
        correoT = (TextView) root.findViewById(R.id.correoView);

        datos = (Button) root.findViewById(R.id.actualizar);
        cerrar = (Button) root.findViewById(R.id.btnCerrarSesion);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("correo").getValue().equals(firebaseUser)){
                        nombreT.setText(ds.child("nombre").getValue(String.class));
                        apellidosT.setText(ds.child("apellidos").getValue(String.class));
                        correoT.setText(ds.child("correo").getValue(String.class));
                        edadT.setText(ds.child("edad").getValue(String.class));
                    }
                /*
                if(snapshot.exists()){
                    String nombreS = ""+snapshot.child("nombre").getValue();
                    String apellidosS = ""+snapshot.child("apellidos").getValue();
                    String correoS = ""+snapshot.child("correo").getValue();
                    String edadS =  ""+snapshot.child("edad").getValue();
                    //String imagen = ""+snapshot.child("imagen").getValue();

                    nombre.setText(nombreS);
                    apellidos.setText(apellidosS);
                    correo.setText(correoS);
                    edad.setText(edadS);

                   //Glide.with(fotoPerfil).load(imagen);
                    */
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
                try {
                    NotificationsFragment.this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}