package com.samkeet.takeup.users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.samkeet.takeup.R;

import org.json.JSONArray;
import org.json.JSONException;

public class DetailsActivity extends AppCompatActivity {

    private TextView mPlantName,mPlantDetails,mPlantTakeUpStatus,mPlantTakeUpUser,mUserAddedName;
    private ImageView mPlantImage;
    public JSONArray mTreeObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        try {
            mTreeObject = new JSONArray(getIntent().getStringExtra("DATA"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mPlantName = (TextView) findViewById(R.id.plant_name);
        mPlantDetails = (TextView) findViewById(R.id.plant_details);
        mPlantTakeUpStatus = (TextView) findViewById(R.id.plant_take_up_status);
        mPlantTakeUpUser = (TextView) findViewById(R.id.plant_take_up_user);
        mPlantImage = (ImageView) findViewById(R.id.plant_image);
    }
}
