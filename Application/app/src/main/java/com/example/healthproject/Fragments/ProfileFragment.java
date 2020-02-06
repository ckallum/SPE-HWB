package com.example.healthproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthproject.Activity.MainActivity;
import com.example.healthproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    Button logoutButton;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthS;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

        EditText emailText = (EditText) rootView.findViewById(R.id.emailBox);
        final EditText passwordText = rootView.findViewById(R.id.password);
        final EditText newPasswordText = rootView.findViewById(R.id.newPasswordBox);
        //final EditText rePasswordText = rootView.findViewById(R.id.rePasswordBox);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();

            emailText.setText(userEmail);

            // User is signed in
        } else {
            // No user is signed in
        }









        logoutButton = rootView.findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toMain = new Intent(getActivity(), MainActivity.class);

                startActivity(toMain);
            }
        });
        return  rootView;

    }


}