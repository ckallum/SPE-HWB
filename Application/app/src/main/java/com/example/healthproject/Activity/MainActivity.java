package com.example.healthproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.healthproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    EditText UsernameET, PasswordET;
    Button loginButton;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();


        UsernameET = (EditText) findViewById(R.id.email);
        PasswordET = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.buttonlogin);

        TextView signUpText = (TextView) findViewById(R.id.signUpLink);


        mAuthS = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if(firebaseUser != null){
                    Toast.makeText(MainActivity.this, "You are logged in already",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, NavigationActivity.class);

                    startActivity(i);
                }

                else{
                    Toast.makeText(MainActivity.this, "Please log in",Toast.LENGTH_SHORT).show();


                }

            }
        };//stop


        signUpText.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                              startActivity(intent);

                                          }
                                      }
        );
    }


    public void onLogin(View view) {
        String username = UsernameET.getText().toString();
        String password = PasswordET.getText().toString();
        String type = "login";


        if (username.isEmpty()) {
            UsernameET.setError("Please enter an email");
            UsernameET.requestFocus();
        } else if (password.isEmpty()) {
            PasswordET.setError("Please enter a password");
            PasswordET.requestFocus();
        } else if (username.isEmpty() && username.isEmpty()) {
            Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else if (!(username.isEmpty()) && !(password.isEmpty())) {
            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();


                }
                    else{
                        Intent toHome = new Intent(MainActivity.this, NavigationActivity.class);
                        startActivity(toHome);
                    }
            }

            });

        }





        else {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthS);
    }
}

