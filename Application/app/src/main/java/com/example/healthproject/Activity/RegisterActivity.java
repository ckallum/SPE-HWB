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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    EditText username, password, email;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthS;

   EditText passReenter = (EditText)findViewById(R.id.et_password2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
                                              Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                              startActivity(intent);

                                          }
                                      }
        );



    }

    public void onRegister(View view) {
        final String str_username = username.getText().toString();
        String str_password = password.getText().toString();
        String str_email = email.getText().toString();
        String str_retypepass = passReenter.getText().toString();
        String type = "signup";

        if (str_email.isEmpty()) {
            email.setError("Please enter an email");
            email.requestFocus();
        } else if (str_password.isEmpty()) {
            password.setError("Please enter a password");
            password.requestFocus();
        } else if (str_email.isEmpty() && str_password.isEmpty() && str_retypepass.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }else if(str_password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password is too short ", Toast.LENGTH_SHORT).show();
        }else if(!(isValidPassword(str_password))) {
            Toast.makeText(RegisterActivity.this, "Please use a combo symbols/numbers", Toast.LENGTH_SHORT).show();
        }else if(!(str_password.equals(str_retypepass))){
            Toast.makeText(RegisterActivity.this, "Re-entered Password is incorrect", Toast.LENGTH_SHORT).show();
        } else if (!(str_email.isEmpty()) && !(str_password.isEmpty()) && str_password.equals(str_retypepass)) {
            mAuth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Sign up failed,Please try again", Toast.LENGTH_SHORT).show();

                    } else {
                       // String user_id = mAuth.getCurrentUser().getUid();
                        //final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

                        Map newPost = new HashMap();
                        newPost.put("username",str_username);

                        //current_user_db.setValue(newPost);

                        Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                    }

                }
            });


        } else {
            Toast.makeText(RegisterActivity.this, "Please fill all boxes", Toast.LENGTH_SHORT).show();
        }


    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}



