package com.samkeet.takeup.organisation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.samkeet.takeup.R;

import org.json.JSONArray;
import org.json.JSONException;

public class DetailsActivity extends AppCompatActivity {

    public JSONArray mTreeObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        try {
            mTreeObject = new JSONArray(getIntent().getStringExtra("DATA"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
