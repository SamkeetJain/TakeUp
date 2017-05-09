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

    private TextInputLayout tName;
    private TextInputLayout tEmail;
    private TextInputLayout tMobileNumber;
    private TextInputLayout tAddress;

    private Button mCreateAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobileNumber = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);

        tName = (TextInputLayout) findViewById(R.id.text_name);
        tEmail = (TextInputLayout) findViewById(R.id.text_email);
        tMobileNumber = (TextInputLayout) findViewById(R.id.text_mobile);
        tAddress = (TextInputLayout) findViewById(R.id.text_address);

        mCreateAccount = (Button) findViewById(R.id.createaccount);
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean valid(){
        mName = name.getText().toString().trim();
        mEmail = email.getText().toString().trim();
        mMobileNumber = mobileNumber.getText().toString().trim();
        mAddress = address.getText().toString().trim();


        if(mName.isEmpty()){
            tName.setError("Name cannot be empty");
            requestFocus(name);
            return false;
        }else{
            tName.setErrorEnabled(false);
        }

        if(mEmail.isEmpty() || !isValidEmail(mEmail)){
            tEmail.setError("Enter valid email");
            requestFocus(email);
            return false;
        }else{
            tEmail.setErrorEnabled(false);
        }

        if(mMobileNumber.isEmpty() || mMobileNumber.length() != 10){
            tMobileNumber.setError("Enter valid mobile number");
            requestFocus(mobileNumber);
            return false;
        }else{
            tMobileNumber.setErrorEnabled(false);
        }

        if(mAddress.isEmpty()){
            tAddress.setError("Address cannot be empty");
            requestFocus(address);
            return false;
        }else{
            tAddress.setErrorEnabled(false);
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
