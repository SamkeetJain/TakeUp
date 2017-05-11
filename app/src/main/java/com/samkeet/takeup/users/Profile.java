package com.samkeet.takeup.users;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.samkeet.takeup.Constants;
import com.samkeet.takeup.R;

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

public class Profile extends AppCompatActivity {

    private EditText tName;
    private EditText tEmail;
    private EditText tPhoneNo;
    private EditText tAddress;
    private RadioButton rGenderMale;
    private RadioButton rGenderFemmale;
    private EditText tDateOfBirth;

    public String name, email, phoneno, address, dob, gender;

    private Button bEdit;
    private SpotsDialog pd;
    private Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialogContext = this;

        tName = (EditText) findViewById(R.id.setName);
        tEmail = (EditText) findViewById(R.id.setEmail);
        tPhoneNo = (EditText) findViewById(R.id.setPhone);
        tAddress = (EditText) findViewById(R.id.setAddress);
        rGenderFemmale = (RadioButton) findViewById(R.id.setGenderFemale);
        rGenderMale = (RadioButton) findViewById(R.id.setGenderMale);
        tDateOfBirth = (EditText) findViewById(R.id.setDateOfBirth);

        bEdit = (Button) findViewById(R.id.edit_button);


        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = bEdit.getText().toString().trim();
                if (status.equals("edit")) {
                    bEdit.setText("save");
                    enableEditText();
                } else {
                    name = tName.getText().toString();
                    phoneno = tPhoneNo.getText().toString();
                    email = tEmail.getText().toString();
                    address = tAddress.getText().toString();
                    dob = tDateOfBirth.getText().toString();

                    if (rGenderMale.isChecked()) {
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }

                    PutData putData = new PutData();
                    putData.execute();
                    bEdit.setText("edit");
                    disableEditText();
                }
            }
        });

        GetData getData = new GetData();
        getData.execute();
    }

    private void disableEditText() {
        tName.setEnabled(false);
        tEmail.setEnabled(false);
        tPhoneNo.setEnabled(false);
        tAddress.setEnabled(false);
        rGenderFemmale.setEnabled(false);
        rGenderMale.setEnabled(false);
        tDateOfBirth.setEnabled(false);
    }

    private void enableEditText() {
        tName.setEnabled(true);
        tEmail.setEnabled(true);
        tPhoneNo.setEnabled(true);
        tAddress.setEnabled(true);
        rGenderFemmale.setEnabled(true);
        rGenderMale.setEnabled(true);
        tDateOfBirth.setEnabled(true);
    }

    private class GetData extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.BASE + Constants.URLs.GET_PROFILE);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN());
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

                    JSONObject jsonObject = new JSONObject(jsonResults.toString());
                    name = jsonObject.getString("Full_Name");
                    address = jsonObject.getString("Address");
                    dob = jsonObject.getString("Dob");
                    phoneno = jsonObject.getString("Phone_No");
                    email = jsonObject.getString("UserID");
                    gender = jsonObject.getString("Gender");

                    authenticationError = false;
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
                forward();
            }

        }
    }

    private class PutData extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.BASE + Constants.URLs.PUT_PROFILE);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("UserID", email)
                        .appendQueryParameter("Phone_No", phoneno)
                        .appendQueryParameter("Address", address)
                        .appendQueryParameter("Full_Name", name)
                        .appendQueryParameter("Dob", dob)
                        .appendQueryParameter("Gender", gender);
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
                        errorMessage = status;
                    } else {
                        authenticationError = true;
                        errorMessage = status;
                    }
                    authenticationError = false;
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
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    public void forward() {

        tName.setText(name);
        tAddress.setText(address);
        tDateOfBirth.setText(dob);
        tEmail.setText(email);
        tPhoneNo.setText(phoneno);

        if (gender.equals("Male")) {
            rGenderMale.setChecked(true);
            rGenderFemmale.setChecked(false);
        } else {
            rGenderMale.setChecked(false);
            rGenderFemmale.setChecked(true);
        }
        disableEditText();
    }
}
