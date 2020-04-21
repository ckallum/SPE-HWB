package com.example.healthproject.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthproject.R;
import com.example.healthproject.View.FirebaseAuthResult;
import com.example.healthproject.View.FormState;
import com.example.healthproject.View.UserView;
import com.example.healthproject.View.ViewModelController;
import com.example.healthproject.View.ViewModelFactory;

public class RegisterActivity extends AppCompatActivity {
    private ViewModelController registerViewModel;
    //TODO Add to firestore users entry->collection.("admins")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText emailEditText = findViewById(R.id.et_email);
        final EditText passwordEditText = findViewById(R.id.et_password);
        final EditText password2 = findViewById(R.id.et_password2);
        final Button registerButton = findViewById(R.id.registerButton);
        final TextView signInText = findViewById(R.id.signInLink);
        registerViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ViewModelController.class);
        signInText.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                              startActivity(intent);
                                              finish();

                                          }
                                      }
        );
        registerViewModel.getFormState().observe(this, new Observer<
                FormState>() {
            @Override
            public void onChanged(@Nullable FormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }else {
                    emailEditText.setError(null);
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                } else {
                    emailEditText.setError(null);
                }
            }
        });
        registerViewModel.getAuthResult().observe(this, new Observer<FirebaseAuthResult>() {
            @Override
            public void onChanged(@Nullable FirebaseAuthResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                if (registerResult.getError() != null) {
                    showRegisterFailed(registerResult.getError());
                }
                if (registerResult.getSuccess() != null) {
                    userRegistered(registerResult.getSuccess());

                }
                setResult(Activity.RESULT_OK);

//                Complete and destroy forgot activity once successful
                finish();
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
                registerViewModel.registerDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), password2.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.register(emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerViewModel.register(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void userRegistered(UserView success) {
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


