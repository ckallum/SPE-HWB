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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    EditText username, password, email,password2;
    FirebaseAuth mAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        TextView signInText = findViewById(R.id.signInLink);
        username = (EditText)findViewById(R.id.et_username);
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        password2 = (EditText)findViewById(R.id.et_password2);


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
        //once register button is pressed
        final String str_username = username.getText().toString();
        final String str_password = password.getText().toString();
        final String str_email = email.getText().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        //String str_retypepass = passReenter.getText().toString();
        String type = "signup";


        if (str_username.isEmpty()){
            username.setError("Please enter a username");
            username.requestFocus();
        }else if (str_email.isEmpty()) {                    //check each text field
            email.setError("Please enter an email");
            email.requestFocus();
        } else if (str_password.isEmpty()) {
            password.setError("Please enter a password");
            password.requestFocus();
        }else if (str_email.isEmpty() && str_password.isEmpty() && str_username.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }else if(str_password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password is too short ", Toast.LENGTH_SHORT).show();
        }else if(!(isValidPassword(str_password))) {
            Toast.makeText(RegisterActivity.this, "Please use a combo symbols/numbers", Toast.LENGTH_SHORT).show();

        }else if(!(str_password.equals(password2.getText().toString()))){
            Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();

        } else if (!(str_email.isEmpty()) && !(str_password.isEmpty()) && !(str_username.isEmpty()) && str_password.equals(password2.getText().toString())) { //check username is not taken already

            ref.orderByChild("username").equalTo(str_username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Toast.makeText(RegisterActivity.this, "Username taken ", Toast.LENGTH_SHORT).show();
                    }

                    else{

                        mAuth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Sign up failed,Please try again", Toast.LENGTH_SHORT).show();

                                } else {


                                    User user = new User(str_username,str_email,false);

                                    user.getEmail();
                                    user.getUsername();
                                    user.getIsAdmin();

                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {


                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "User Added", Toast.LENGTH_SHORT).show();


                                            }
                                            else {
                                                Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });



                                    //   current_user_db.child("users").child(user_id).setValue(newPost);

                                    //Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                                }

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(RegisterActivity.this, "Please fill all boxes", Toast.LENGTH_SHORT).show();
        }


    }


    public static boolean isValidPassword(final String password) {     //password validation

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


}



