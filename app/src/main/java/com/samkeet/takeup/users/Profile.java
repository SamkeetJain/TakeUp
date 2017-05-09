package com.samkeet.takeup.users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.samkeet.takeup.R;

public class Profile extends AppCompatActivity {

    private EditText tName;
    private EditText tEmail;
    private EditText tPhoneNo;
    private EditText tAddress;
    private EditText tGender;
    private EditText tDateOfBirth;

    private Button bEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        tName = (EditText) findViewById(R.id.setName);
        tEmail = (EditText) findViewById(R.id.setEmail);
        tPhoneNo = (EditText) findViewById(R.id.setPhone);
        tAddress = (EditText) findViewById(R.id.setAddress);
        tGender = (EditText) findViewById(R.id.setGender);
        tDateOfBirth = (EditText) findViewById(R.id.setDateOfBirth);

        bEdit = (Button) findViewById(R.id.edit_button);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = bEdit.getText().toString().trim();
                if(status.equals("edit")){
                    bEdit.setText("save");
                    enableEditText();
                }else{
                    bEdit.setText("edit");
                    disableEditText();
                }
            }
        });

        disableEditText();
    }

    private void disableEditText(){
        tName.setEnabled(false);
        tEmail.setEnabled(false);
        tPhoneNo.setEnabled(false);
        tAddress.setEnabled(false);
        tGender.setEnabled(false);
        tDateOfBirth.setEnabled(false);
    }

    private void enableEditText(){
        tName.setEnabled(true);
        tEmail.setEnabled(true);
        tPhoneNo.setEnabled(true);
        tAddress.setEnabled(true);
        tGender.setEnabled(true);
        tDateOfBirth.setEnabled(true);
    }
}
