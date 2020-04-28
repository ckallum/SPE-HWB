package com.example.healthproject.Activity.Navigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.healthproject.Fragments.BookingsFragment;
import com.example.healthproject.Fragments.CreateEventFragment;
import com.example.healthproject.Fragments.EventsFragment;
import com.example.healthproject.Fragments.HomeFragment;
import com.example.healthproject.Fragments.ManageFragment;
import com.example.healthproject.Fragments.ProfileFragment;
import com.example.healthproject.Fragments.VenuesFragment;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =           //select fragment
            menuItem -> {
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
                    case R.id.manage:
                        selectedFragment = new ManageFragment();
                        break;
                    case R.id.add:
                        selectedFragment = new CreateEventFragment();
                        break;
                    default:
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());

        BottomNavigationView bottomNav;
        if (user.isAdmin()){
            setContentView(R.layout.activity_admin);
            bottomNav = findViewById(R.id.admin_navbar);
        }else{
            setContentView(R.layout.activity_main);
            bottomNav = findViewById(R.id.user_navbar);
        }
        assert bottomNav !=null;

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  //a fragment container will store each activity
                new HomeFragment()).commit();
    }


}
