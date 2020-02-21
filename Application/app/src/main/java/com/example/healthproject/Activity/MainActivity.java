package com.example.healthproject.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthproject.Activity.Navigation.UserNavigationActivity;
import com.example.healthproject.Data.FirebaseDataSource;
import com.example.healthproject.Data.GlobalUser;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private GlobalUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());
        if (!user.isLoggedIn()){
            finish();
        }

    }

    @Override
    protected void onStart(){
        super.onStart();
        startActivity(new Intent(MainActivity.this, UserNavigationActivity.class));
//
//        if (user.isAdmin()){
//            startActivity(new Intent(MainActivity.this, UserNavigationActivity.class));
//        }
    }
}
