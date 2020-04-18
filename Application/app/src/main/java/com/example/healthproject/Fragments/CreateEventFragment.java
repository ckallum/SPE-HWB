package com.example.healthproject.Fragments;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthproject.Activity.ForgotActivity;
import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.R;
import com.example.healthproject.View.FirebaseAuthResult;
import com.example.healthproject.View.FormState;
import com.example.healthproject.View.UserView;
import com.example.healthproject.View.ViewModelController;
import com.example.healthproject.View.ViewModelFactory;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class CreateEventFragment extends Fragment {
    private ViewModelController forgotViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create);

        final Button forgotButton = findViewById(R.id.sendEmail);              // create instances of buttons/txt fields
        final EditText emailEditText = findViewById(R.id.emailTxtBox);
        Button backButton = findViewById(R.id.backButton);


        forgotViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ViewModelController.class);
        backButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                                              startActivity(intent);
                                              finish();
                                          }
                                      }
        );
        forgotViewModel.getFormState().observe(this, new Observer<FormState>() {
            @Override
            public void onChanged(@Nullable FormState forgotFormState) {
                if (forgotFormState == null) {
                    return;
                }
                forgotButton.setEnabled(forgotFormState.isDataValid());
                if (forgotFormState.getEmailError() != null) {
                    emailEditText.setError(getString(forgotFormState.getEmailError()));
                }

            }
        });
        forgotViewModel.getAuthResult().observe(this, new Observer<FirebaseAuthResult>() {
            @Override
            public void onChanged(@Nullable FirebaseAuthResult forgotResult) {
                if (forgotResult == null) {
                    return;
                }
                if (forgotResult.getError() != null) {
                    showForgotFailed(forgotResult.getError());
                }
                if (forgotResult.getSuccess() != null) {
                    userForgot(forgotResult.getSuccess());

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
                forgotViewModel.forgotDataChanged(emailEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    forgotViewModel.forgot(emailEditText.getText().toString());
                }
                return false;
            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotViewModel.forgot(emailEditText.getText().toString());
            }
        });

    }

    private void userForgot(UserView success) {
        Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showForgotFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}


}
