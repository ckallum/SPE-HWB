package com.example.healthproject.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthproject.Activity.MainActivity;
import com.example.healthproject.Activity.NavigationActivity;
import com.example.healthproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final int CHOOSE_IMAGE = 808;
    private Button logoutButton;
    FirebaseAuth mAuth;

    ImageView camera;
    EditText username;
    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);
        logoutButton = rootView.findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toMain = new Intent(getActivity(), MainActivity.class);

                startActivity(toMain);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        camera = rootView.findViewById(R.id.cameraImage);
        username = rootView.findViewById(R.id.usernameBox);
        progressBar = rootView.findViewById(R.id.progressBar);

        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        rootView.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

        return rootView;

    }

    private void saveUserInformation() {
        String displayName = username.getText().toString();

        if(displayName.isEmpty()) {
            username.setError("Name required");
            username.requestFocus();
            return;

        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl)).build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), uriProfileImage);
                camera.setImageBitmap(bitmap);

                uploadImageToFireBaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFireBaseStorage() {
        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference("profileimages/"+System.currentTimeMillis()+".jpg");

        if(uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            storageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);

                    profileImageUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose profile picture"), CHOOSE_IMAGE);
    }


}