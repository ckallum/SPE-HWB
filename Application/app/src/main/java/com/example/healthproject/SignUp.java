package com.example.healthproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {
    EditText username, password, email;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView signInText = findViewById(R.id.signInLink);
        username = (EditText)findViewById(R.id.et_username);
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();


//        signInText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//                }
//            }
//        );



        signInText.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(SignUp.this, MainActivity.class);
                                              startActivity(intent);

                                          }
                                      }
        );



    }

    public void onRegister(View view) {
        final String str_username = username.getText().toString();
        String str_password = password.getText().toString();
        String str_email = email.getText().toString();
        String type = "signup";

        if (str_email.isEmpty()) {
            email.setError("Please enter an email");
            email.requestFocus();
        } else if (str_password.isEmpty()) {
            password.setError("Please enter a password");
            password.requestFocus();
        } else if (str_email.isEmpty() && str_password.isEmpty()) {
            Toast.makeText(SignUp.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else if (!(str_email.isEmpty()) && !(str_password.isEmpty())) {
            mAuth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Sign up failed,Please try again", Toast.LENGTH_SHORT).show();

                    } else {
                       // String user_id = mAuth.getCurrentUser().getUid();
                        //final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

                        Map newPost = new HashMap();
                        newPost.put("username",str_username);

                        //current_user_db.setValue(newPost);

                        Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(SignUp.this, activity_homescreen.class));
                    }

                }
            });


        } else {
            Toast.makeText(SignUp.this, "Please fill all boxes", Toast.LENGTH_SHORT).show();
        }


    }
}



