package com.samkeet.takeup.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.takeup.Constants;
import com.samkeet.takeup.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

/**
 * Created by Hi Abhishek on 5/9/2017.
 */

public class SignupActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String mName;
    private String mEmail;
    private String mMobileNumber;
    private String mAddress;
    private String mPassword;
    private String mCPassword;

    private EditText name;
    private EditText password;
    private EditText cpassword;
    private EditText email;
    private EditText mobileNumber;
    private EditText address;
    private TextView dob;

    private TextInputLayout tName;
    private TextInputLayout tEmail;
    private TextInputLayout tMobileNumber;
    private TextInputLayout tAddress;
    private TextInputLayout tPassword;
    private TextInputLayout tCpassword;

    private Button mCreateAccount;

    private SpotsDialog pd;
    private Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressDialogContext = this;

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobileNumber = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        dob = (TextView) findViewById(R.id.datebutton);

        tName = (TextInputLayout) findViewById(R.id.text_name);
        tEmail = (TextInputLayout) findViewById(R.id.text_email);
        tMobileNumber = (TextInputLayout) findViewById(R.id.text_mobile);
        tAddress = (TextInputLayout) findViewById(R.id.text_address);
        tPassword = (TextInputLayout) findViewById(R.id.text_password);
        tCpassword = (TextInputLayout) findViewById(R.id.text_cpassword);

        mCreateAccount = (Button) findViewById(R.id.createaccount);
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    if (valid()) {
                        Signup signup = new Signup();
                        signup.execute();
                    }
                }
            }
        });

    }

    public void dateButton(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SignupActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private boolean valid() {
        mName = name.getText().toString().trim();
        mEmail = email.getText().toString().trim();
        mMobileNumber = mobileNumber.getText().toString().trim();
        mAddress = address.getText().toString().trim();
        mPassword = password.getText().toString().trim();
        mCPassword = cpassword.getText().toString().trim();


        if (mName.isEmpty()) {
            tName.setError("Name cannot be empty");
            requestFocus(name);
            return false;
        } else {
            tName.setErrorEnabled(false);
        }

        if (mEmail.isEmpty() || !isValidEmail(mEmail)) {
            tEmail.setError("Enter valid email");
            requestFocus(email);
            return false;
        } else {
            tEmail.setErrorEnabled(false);
        }

        if (mPassword.isEmpty()) {
            tPassword.setError("Enter valid Password");
            requestFocus(password);
            return false;
        } else {
            tPassword.setErrorEnabled(false);
        }

        if (mCPassword.isEmpty()) {
            tCpassword.setError("Enter valid Password");
            requestFocus(cpassword);
            return false;
        } else {
            tCpassword.setErrorEnabled(false);
        }

        if (mMobileNumber.isEmpty() || mMobileNumber.length() != 10) {
            tMobileNumber.setError("Enter valid mobile number");
            requestFocus(mobileNumber);
            return false;
        } else {
            tMobileNumber.setErrorEnabled(false);
        }

        if (mAddress.isEmpty()) {
            tAddress.setError("Address cannot be empty");
            requestFocus(address);
            return false;
        } else {
            tAddress.setErrorEnabled(false);
        }

        if (!mPassword.equals(mCPassword)) {
            tCpassword.setError("Passwords doesn't match");
            requestFocus(cpassword);
            return false;
        } else {
            tCpassword.setErrorEnabled(false);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String fullDate;
        String dayofmonth = "" + dayOfMonth;
        String monthofyear = "" + (++monthOfYear);
        if (dayOfMonth < 10)
            dayofmonth = "0" + dayOfMonth;
        if (++monthOfYear < 10) {
            monthofyear = "0" + monthOfYear;
        }

        fullDate = year + "-" + (monthofyear) + "-" + dayofmonth;

        dob.setText(fullDate);
    }

    private class Signup extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.BASE + Constants.URLs.SIGNUP);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("UserID", mEmail)
                        .appendQueryParameter("Password", mPassword)
                        .appendQueryParameter("Phone_No", mMobileNumber)
                        .appendQueryParameter("Address", mAddress)
                        .appendQueryParameter("Full_Name", mName)
                        .appendQueryParameter("Dob", "fsd")
                        .appendQueryParameter("Gender", "Male");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(_data.build().getEncodedQuery());
                writer.flush();
                writer.close();

                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                StringBuilder jsonResults = new StringBuilder();
                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                connection.disconnect();
                authenticationError = jsonResults.toString().contains("Authentication Error");

                if (authenticationError) {
                    errorMessage = jsonResults.toString();
                } else {
                    JSONObject jsonObj = new JSONObject(jsonResults.toString());
                    String status = jsonObj.getString("status");
                    if (status.equals("success")) {
                        authenticationError = false;
                    } else {
                        authenticationError = true;
                        errorMessage = status;
                    }
                }

                return 1;
            } catch (FileNotFoundException | ConnectException | UnknownHostException ex) {
                authenticationError = true;
                errorMessage = "Please check internet connection.\nConnection to server terminated.";
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (pd != null) {
                pd.dismiss();
            }
            if (authenticationError) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }
}
