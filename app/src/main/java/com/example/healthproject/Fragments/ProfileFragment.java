package com.example.healthproject.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final int CHOOSE_IMAGE = 808;
    private FirebaseAuth mAuth;

    private ImageView camera;
    private EditText username;
    private EditText email;
    private EditText password;
    private Uri uriProfileImage;
    private ProgressBar progressBar;
    private String profileImageUrl;
    private GlobalUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user = GlobalUser.getInstance(new FirebaseDataSource());

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Button logoutButton = rootView.findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(v -> {
            user.logout();

            Intent toMain = new Intent(getActivity(), LoginActivity.class);
            toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            toMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(toMain);
        });

        mAuth = FirebaseAuth.getInstance();

        camera = rootView.findViewById(R.id.cameraImage);
        username = rootView.findViewById(R.id.usernameBox);
        email = rootView.findViewById(R.id.emailBox);
        password = rootView.findViewById(R.id.passwordBox);

        progressBar = rootView.findViewById(R.id.progressBar);

        camera.setOnClickListener(v -> showImageChooser());

        loadUserInformation();

        rootView.findViewById(R.id.button_save).setOnClickListener(v -> saveUserInformation());

        return rootView;

    }

    private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                RequestOptions options = new RequestOptions();
                Glide.with(camera)
                        .load(user.getPhotoUrl().toString())
                        .apply(options.circleCrop())
                        .into(camera);
            }

            if (user.getDisplayName() != null) {
                username.setText(user.getDisplayName());
            }

            if (user.getEmail() != null) {
                email.setText(user.getEmail());
            }
        }
    }

    private void saveUserInformation() {
        String displayName = username.getText().toString();

        if (displayName.isEmpty()) {
            username.setError("Name required");
            username.requestFocus();
            return;

        }

        FirebaseUser mUser = mAuth.getCurrentUser();
        UserProfileChangeRequest profile;

        if (mUser != null) {
            if (profileImageUrl != null) {
                profile = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .setPhotoUri(uriProfileImage)
                        .build();
            } else {
                profile = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build();
            }

            if (!password.getText().toString().isEmpty()) {
                user.updatePassword(password.getText().toString());
            }
            user.updateDisplayName(displayName);
            mUser.updateProfile(profile).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uriProfileImage);
                camera.setImageBitmap(bitmap);

                uploadImageToFireBaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFireBaseStorage() {

        final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profileimages/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);

            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressBar.setVisibility(View.GONE);

                        profileImageRef.getDownloadUrl().addOnCompleteListener(task -> {
                            profileImageUrl = Objects.requireNonNull(task.getResult()).toString();
                            Log.i("URL", profileImageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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