package com.example.healthproject.Activity.Navigation;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.healthproject.Fragments.BookingsFragment;
import com.example.healthproject.Fragments.EventsFragment;
import com.example.healthproject.Fragments.HomeFragment;
import com.example.healthproject.Fragments.ProfileFragment;
import com.example.healthproject.Fragments.VenuesFragment;
import com.example.healthproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserNavigationActivity extends AppCompatActivity {


    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        bottomNav = findViewById(R.id.user_navbar);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  //a fragment container will store each activity
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =           //select fragment
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.venues:
                            selectedFragment = new VenuesFragment();
                            break;
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.booking:
                            selectedFragment = new BookingsFragment();
                            break;
                        case R.id.events:
                            selectedFragment = new EventsFragment();
                            break;
                        case R.id.profile:
                            selectedFragment = new ProfileFragment();
                            break;


                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
