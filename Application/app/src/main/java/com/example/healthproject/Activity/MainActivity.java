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


        UsernameET = (EditText) findViewById(R.id.email);       //create instances of each text box
        PasswordET = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.buttonlogin);

        TextView signUpText = (TextView) findViewById(R.id.signUpLink);
        TextView forgotPassText = (TextView) findViewById(R.id.forgotPassLink);


        mAuthS = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {          //check if user is logged in already
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


        forgotPassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                 //allows text view to be clickable , if clicked, go to forgot password activity
                Intent intent = new Intent(MainActivity.this, ForgotPassActivity.class);
                startActivity(intent);

            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                              startActivity(intent);

                                          }
                                      }
        );
    }


    public void onLogin(View view) {                             // if log in button is pressed, do the following
        String username = UsernameET.getText().toString();
        String password = PasswordET.getText().toString();
        String type = "login";


        if (username.isEmpty()) {                              //check if user has entered something in either text boxes
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
                public void onComplete(@NonNull Task<AuthResult> task) {     //add to firebase database

                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();                    //if the task fails, i.e. user already exists


                }
                    else{
                        Intent toHome = new Intent(MainActivity.this, NavigationActivity.class);             // open home activity if successful
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
    protected void onStart() {         //check if user is already signed in
        super.onStart();
        mAuth.addAuthStateListener(mAuthS);
    }
}

