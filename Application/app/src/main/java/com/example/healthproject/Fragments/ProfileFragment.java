package com.example.healthproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthproject.Activity.MainActivity;
import com.example.healthproject.Activity.RegisterActivity;
import com.example.healthproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileFragment extends Fragment {
    Button logoutButton;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthS;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

        final EditText emailText = (EditText) rootView.findViewById(R.id.emailBox);     //rootView looks at what's on the fragment, not the navigation activity
        final EditText passwordText = rootView.findViewById(R.id.password);
        final EditText newPasswordText = rootView.findViewById(R.id.newPasswordBox);
        Button updateBtn = rootView.findViewById(R.id.updateButton);
        final EditText usernameBox = rootView.findViewById(R.id.usernameBox);

        //final EditText rePasswordText = rootView.findViewById(R.id.rePasswordBox);


       final  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       String userid = user.getUid();




        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");


        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("username").getValue().toString();
                usernameBox.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "No Username", Toast.LENGTH_SHORT).show();


            }
        });


        if (user != null) {
            String userEmail = user.getEmail();

            emailText.setText(userEmail);

            // User is signed in
        } else {
            // No user is signed in
        }





        logoutButton = rootView.findViewById(R.id.logoutBtn);                        //log the user out
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toMain = new Intent(getActivity(), MainActivity.class);       // open MainActivity

                startActivity(toMain);
            }
        });
        return  rootView;

    }


}