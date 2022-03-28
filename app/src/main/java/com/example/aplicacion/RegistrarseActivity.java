package com.example.aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarseActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    TextView alreadyHaveAccount;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    Button btnRegister;
    String validEmails="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Para encontrar widgets y botones por id
        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputPassword2);
        btnRegister=findViewById(R.id.btnRegister);

        progressDialog=new ProgressDialog(this);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrarseActivity.this,MainActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();


        if(!email.matches(validEmails)){
            inputEmail.setError("Introduzca un email correcto");
            inputEmail.requestFocus();
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Introduzca una contraseña válida");
        } else if(!password.equals(confirmPassword)){
            inputConfirmPassword.setError("Las contraseñas no coincide.");
        } else{
            progressDialog.setMessage("Registro en progreso...");
            progressDialog.setTitle("Registro");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegistrarseActivity.this,"Registro completado",Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegistrarseActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegistrarseActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}