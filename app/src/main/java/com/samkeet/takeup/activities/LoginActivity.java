package com.samkeet.takeup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.samkeet.takeup.R;

/**
 * Created by Hi Abhishek on 5/9/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private String email, password;
    private EditText mEmail, mPassword;
    private TextInputLayout tEmail, tPassword;
    private Button mLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        tEmail = (TextInputLayout) findViewById(R.id.text_email);
        tPassword = (TextInputLayout) findViewById(R.id.text_password);

        mLogin = (Button) findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    private boolean valid() {
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            tEmail.setError("Enter valid Email");
            requestFocus(mEmail);
            return false;
        } else {
            tEmail.setErrorEnabled(false);
        }
        if (password.isEmpty() || password.length() > 32) {
            tPassword.setError("Enter valid password (32 char max)");
            requestFocus(mPassword);
            return false;
        } else {
            tPassword.setErrorEnabled(false);
        }
        return true;

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
