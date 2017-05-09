package com.samkeet.takeup.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import com.samkeet.takeup.Constants;
import com.samkeet.takeup.R;
import com.samkeet.takeup.organisation.OrgActivity;
import com.samkeet.takeup.users.UserActivity;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import dmax.dialog.SpotsDialog;

/**
 * Created by Hi Abhishek on 5/9/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private String email, password;
    private EditText mEmail, mPassword;
    private TextInputLayout tEmail, tPassword;
    private Button mLogin;
    private SpotsDialog pd;
    private Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";
    public String token, auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialogContext = this;

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        tEmail = (TextInputLayout) findViewById(R.id.text_email);
        tPassword = (TextInputLayout) findViewById(R.id.text_password);

        mLogin = (Button) findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()) {

                    Login login = new Login();
                    login.execute();
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

    public void forward() {
        if (auth.equals("ORG")) {
            Intent intent = new Intent(getApplicationContext(), OrgActivity.class);
            startActivity(intent);
            finish();
        } else if (auth.equals("USER")) {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "USER_TYPE mismatch", Toast.LENGTH_SHORT).show();
            Constants.SharedPreferenceData.clearData();
        }
    }

    private class Login extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.BASE + Constants.URLs.LOGIN);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("UserID", email)
                        .appendQueryParameter("Password", password);
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
                        token = jsonObj.getString("token");
                        auth = jsonObj.getString("auth");
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
                Constants.SharedPreferenceData.setIsLoggedIn(token);
                Constants.SharedPreferenceData.setTOKEN(token);
                Constants.SharedPreferenceData.setEMAIL(email);
                Constants.SharedPreferenceData.setAUTH(auth);
                forward();
            }

        }
    }

}
