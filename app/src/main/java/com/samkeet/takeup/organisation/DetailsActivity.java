package com.samkeet.takeup.organisation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.samkeet.takeup.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    private TextView mPlantName, mPlantDetails, mPlantTakeUpStatus, mPlantTakeUpUser, mUserAddedName;
    private ImageView mPlantImage;
    public JSONObject mTreeObject;
    private String name, details, status, user, addedname, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        try {
            mTreeObject = new JSONObject(getIntent().getStringExtra("DATA"));
            name = mTreeObject.getString("Plant_Name");
            details = mTreeObject.getString("Plant_Details");
            status = mTreeObject.getString("Take_Up_Status");
            user = mTreeObject.getString("Take_Up_User");
            addedname = mTreeObject.getString("User_Name");
            url = mTreeObject.getString("Image_Url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mPlantName = (TextView) findViewById(R.id.plant_name);
        mPlantDetails = (TextView) findViewById(R.id.plant_details);
        mPlantTakeUpStatus = (TextView) findViewById(R.id.plant_take_up_status);
        mPlantTakeUpUser = (TextView) findViewById(R.id.plant_take_up_user);
        mUserAddedName = (TextView) findViewById(R.id.user_added_name);
        mPlantImage = (ImageView) findViewById(R.id.plant_image);

        mPlantName.setText(name);
        mPlantDetails.setText(details);
        mPlantTakeUpStatus.setText(status);
        mPlantTakeUpUser.setText(user);
        mUserAddedName.setText(addedname);
        Picasso.with(getApplicationContext()).load(url).into(mPlantImage);
    }
}
