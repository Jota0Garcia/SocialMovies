package com.example.aplicacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerPass extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonCambiarPass, btnAtras;
    private String emailCamPass = "";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_pass);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.inputEmailRestablecer);
        buttonCambiarPass = (Button) findViewById(R.id.btnRestablecer);
        btnAtras = (Button) findViewById(R.id.btnAtras);

        buttonCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailCamPass = editTextEmail.getText().toString();
                if(!emailCamPass.isEmpty()){
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    resetPassword();
                }else{
                    Toast.makeText(RestablecerPass.this,"Debe ingresar email",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestablecerPass.this, MainActivity.class));
            }
        });
    }

    private void resetPassword() {
        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(emailCamPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RestablecerPass.this,"Se ha enviado un correo para restablecer su contraseña",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RestablecerPass.this,"No se pudo enviar un correo para restablecer su contraseña",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}