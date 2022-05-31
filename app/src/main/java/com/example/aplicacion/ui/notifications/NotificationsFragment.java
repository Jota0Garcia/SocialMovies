package com.example.aplicacion.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicacion.MainActivity;
import com.example.aplicacion.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class NotificationsFragment extends Fragment {


    ImageView fotoPerfilT;
    ImageView fotoPerfil;
    EditText nombreT, apellidosT, edadT;

    TextView correoT,nombreApellidos;

    Button datos, cerrar;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_notifications,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        nombreApellidos = (TextView) root.findViewById(R.id.nombreApellidos);
        fotoPerfilT = (ImageView) root.findViewById(R.id.imageView2);
        nombreT = (EditText) root.findViewById(R.id.nombreView);
        apellidosT = (EditText) root.findViewById(R.id.apellidosView);
        edadT = (EditText) root.findViewById(R.id.edadView);
        correoT = (TextView) root.findViewById(R.id.correoView);
        //correo=firebaseUser.getEmail().toString();
        String id = firebaseAuth.getCurrentUser().getUid();
        datos = (Button) root.findViewById(R.id.actualizar);
        cerrar = (Button) root.findViewById(R.id.btnCerrarSesion);
        Log.v("Tag", "Han cambiado: " + firebaseUser.getUid());


        databaseReference = FirebaseDatabase.getInstance("https://socialmovie-7c309-default-rtdb.europe-west1.firebasedatabase.app").getReference("Usesrs");
        Log.v("Tag", "Han cambiado2: " + databaseReference.child(firebaseUser.getUid()));
        //firebaseDatabase.getReference("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /*
                for(DataSnapshot ds : snapshot.getChildren()){
                    Log.v("Tag", "Han cambiado1: " + ds.child("correo").getValue());
                    if(ds.child("correo").getValue().equals(firebaseUser.getEmail())){
                        nombreT.setText(ds.child("nombre").getValue(String.class));
                        apellidosT.setText(ds.child("apellidos").getValue(String.class));
                        correoT.setText(ds.child("correo").getValue(String.class));
                        edadT.setText(ds.child("edad").getValue(String.class));
                    }

                 */
               // /*
                Log.v("Tag", "Han cambiado1: " + snapshot.getKey());
                Log.v("Tag", "Han cambiado1: " + snapshot.exists());
                Log.v("Tag", "Han cambiado1: " + snapshot.getChildren());
                if(snapshot.exists()){
                    if(!snapshot.child("nombre").getValue().equals(null) ||
                            !snapshot.child("apellidos").getValue().equals(null) ||
                            !snapshot.child("edad").getValue().equals(null)){
                        String nombre = ""+snapshot.child("nombre").getValue();
                        String apellidos = ""+snapshot.child("apellidos").getValue();
                        String correo = ""+snapshot.child("correo").getValue();
                        String edad =  ""+snapshot.child("edad").getValue();

                        nombreApellidos.setText(nombre+" "+apellidos);
                        nombreT.setText(nombre);
                        apellidosT.setText(apellidos);
                        correoT.setText(correo);
                        edadT.setText(edad);
                    }else{
                        String correo = ""+snapshot.child("correo").getValue();
                        nombreApellidos.setText("Nombre Apellidos");
                        nombreT.setText("nombre");
                        apellidosT.setText("apellidos");
                        correoT.setText(correo);
                        edadT.setText("edad");
                    }

                    //String imagen = ""+snapshot.child("imagen").getValue();


                   //Glide.with(fotoPerfil).load(imagen);

                }
               //*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("nombre",nombreT.getText().toString());
                map.put("apellidos",apellidosT.getText().toString());
                map.put("correo",firebaseUser.getEmail());
                map.put("edad",edadT.getText().toString());
                databaseReference.child(firebaseUser.getUid()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(),"Los datos se han actualizado correctament",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Hubo un error al actualizar los datos",Toast.LENGTH_SHORT).show();
                    }
                });

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