package com.samkeet.takeup.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.samkeet.takeup.R;

/**
 * Created by Hi Abhishek on 5/9/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private String mMobileNumber;
    private String mPassword;
    private EditText mobileNumber;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);

        mobileNumber = (EditText) findViewById(R.id.mobile_number);
        password = (EditText) findViewById(R.id.password);


    }

    private void inputValidation(){
        mMobileNumber = mobileNumber.getText().toString().trim();
        mPassword = password.getText().toString().trim();

        if(mMobileNumber.isEmpty() || mMobileNumber.length() != 10){
            requestFocus(mobileNumber);
        }

        if(mPassword.isEmpty()){
            requestFocus(password);
        }

    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
