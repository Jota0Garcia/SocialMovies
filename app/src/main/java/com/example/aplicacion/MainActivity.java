package com.example.aplicacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    EditText inputEmail, inputPassword;
    Button btnLogin;
    String validEmails = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    ProgressDialog progressDialog;
    TextView createNewAccount, forgotPassword;
    ImageView btnGoogle;
    ImageView btnFacebook;

    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Inicialización de widgets obtenidos por id
        createNewAccount = findViewById(R.id.createNewAccount);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnGoogle=findViewById(R.id.btnGoogle);
        btnFacebook=findViewById(R.id.btnFacebook);
        forgotPassword = findViewById(R.id.forgotPassword);
/*
        Button btn=findViewById(R.id.buttonTest);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.i("1","Prueba API Pulsada");
                GetRetrofitResponse();
            }
        });

 */
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrarseActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RestablecerPass.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();

            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GoogleSignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FacebookAuthactivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

/*
            //// ESTO ESTA AQUI DE PRUEBA SOLO
        btn=findViewById(R.id.buttonTest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test","Prueba pulsacion API");
                GetRetrofitResponse();
            }
        });

        ////////////
*/


    }

    /////////// FUNCION SOLO DE PRUEBA
/*
    private void GetRetrofitResponse() {
        MovieApi movieApi= Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall=movieApi.searchMovie(Credentials.API_KEY,"Action",1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()==200){
                    Log.v("Tag","the response"+response.body().toString());
                    List<MovieModel> movies=new ArrayList<>(response.body().getMovies());
                    for (MovieModel movie : movies){
                        Log.v("Tag","The release date: "+movie.getTitle());
                    }
                }
                else{
                    try{
                        Log.v("Tag","Error"+response.errorBody().string());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });

    }

    ///////////////////////////////////
*/


    private void perforLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if (!email.matches(validEmails)) {
            inputEmail.setError("Introduzca un email correcto");
            inputEmail.requestFocus();
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Introduzca una contraseña válida");
        } else {
            progressDialog.setMessage("Login en progreso...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {


                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "Login completado", Toast.LENGTH_SHORT).show();
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(MainActivity.this,MainActivityBottomNav.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}