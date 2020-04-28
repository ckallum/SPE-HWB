package com.example.healthproject.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.R;
import com.example.healthproject.View.UserView;
import com.example.healthproject.View.ViewModelController;
import com.example.healthproject.View.ViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private ViewModelController loginViewModel;
    GlobalUser user;
    FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ViewModelController.class);
        user = GlobalUser.getInstance(new FirebaseDataSource());
        auth = FirebaseAuth.getInstance();
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView signUpText = findViewById(R.id.signUpLink);
        final TextView forgotPassText = findViewById(R.id.forgotPassLink);
        if (user.isLoggedIn()) {
            updateUiWithUser(new UserView(user.getDisplayName()));
        }


        loginViewModel.getFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(v -> auth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(LoginActivity.this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("SUCCESS", "signInWithEmail:success");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference ref = db.collection("users");
                ref.document(usernameEditText.getText().toString()).get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        DocumentSnapshot document = task1.getResult();
                        assert document != null;
                        if (document.exists()) {
                            Log.d("SUCCESS", "DocumentSnapshot data: " + document.getData());
                            loginViewModel.login(usernameEditText.getText().toString(), document.getBoolean("admin"));
                            updateUiWithUser(new UserView(document.getString("displayName")));
                        } else {
                            Log.d("FAILURE", "No such document");
                        }
                    } else {
                        Log.d("FAILURE", "get failed with ", task1.getException());
                    }
                });
            } else {
                // If sign in fails, display a message to the user.
                Log.w("FAIL", "signInWithEmail:failure", task.getException());
                showLoginFailed(R.string.login_failed);

                // ...
            }
            // ...
        })

        );
        signUpText.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), RegisterActivity.class);
            startActivity(i);
        }
        );
        forgotPassText.setOnClickListener(v -> {                 //allows text view to be clickable , if clicked, go to forgot password activity
            Intent i = new Intent(v.getContext(), ForgotActivity.class);
            startActivity(i);
        });

    }

    public void updateUiWithUser(UserView model) {
        String welcome = getString(R.string.welcome) + " ";
        Toast.makeText(getApplicationContext(), welcome + model.getString(), Toast.LENGTH_LONG).show();
        Intent toHome = new Intent(this, MainActivity.class);
        startActivity(toHome);
        finish();
    }

    public void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

}

