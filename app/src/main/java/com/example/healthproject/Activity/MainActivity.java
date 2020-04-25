package com.example.healthproject.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthproject.Activity.Navigation.NavigationActivity;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;

public class MainActivity extends AppCompatActivity {
    private GlobalUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        user = GlobalUser.getInstance(new FirebaseDataSource());
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (!user.isLoggedIn()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            startActivity(new Intent(MainActivity.this, NavigationActivity.class));
        }
        finish();
    }
}
