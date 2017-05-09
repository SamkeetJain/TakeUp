package com.samkeet.takeup.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.samkeet.takeup.R;

/**
 * Created by Hi Abhishek on 5/9/2017.
 */

public class SignupActivity extends AppCompatActivity {

    private String mName;
    private String mEmail;
    private String mMobileNumber;
    private String mAddress;

    private EditText name;
    private EditText email;
    private EditText mobileNumber;
    private EditText address;
    private EditText dateOfBirth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}
