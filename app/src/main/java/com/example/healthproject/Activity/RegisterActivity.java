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

import com.example.healthproject.R;
import com.example.healthproject.View.ViewModelController;
import com.example.healthproject.View.ViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private ViewModelController registerViewModel;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        final EditText email = findViewById(R.id.et_email_register);
        final EditText password = findViewById(R.id.et_password_register);
        final EditText password2 = findViewById(R.id.et_password_register2);
        final Button registerButton = findViewById(R.id.registerButton);
        registerButton.setEnabled(false);
        final TextView signInText = findViewById(R.id.signInLink);
        registerViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ViewModelController.class);
        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);

        }
        );
        registerViewModel.getFormState().observe(this, registerFormState -> {
            if (registerFormState == null) {
                return;
            }
            registerButton.setEnabled(registerFormState.isDataValid());
            if (registerFormState.getEmailError() != null) {
                email.setError(getString(registerFormState.getEmailError()));
            }
            if (registerFormState.getPasswordError() != null) {
                password.setError(getString(registerFormState.getPasswordError()));
            }else{
                password.setError(null);
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
                registerViewModel.registerDataChanged(email.getText().toString(),
                        password.getText().toString(), password2.getText().toString());
            }
        };
        email.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);
        password2.addTextChangedListener(afterTextChangedListener);

        registerButton.setOnClickListener(v -> auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.w("Success", "createUserWithEmail:success");
                // Sign in success, update UI with the signed-in user's information
                registerViewModel.register();
                userRegistered();
            } else {
                // If sign in fails, display a message to the user.
                Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                showRegisterFailed(R.string.register_failed);
            }
            // ...
        }));
    }

    private void userRegistered() {
        Toast.makeText(RegisterActivity.this, "User Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}


