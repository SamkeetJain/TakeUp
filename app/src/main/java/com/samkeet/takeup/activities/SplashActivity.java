package com.samkeet.takeup.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.samkeet.takeup.Constants;
import com.samkeet.takeup.R;
import com.samkeet.takeup.fragments.ImageFragment;
import com.samkeet.takeup.fragments.LoginFragment;
import com.samkeet.takeup.organisation.OrgActivity;
import com.samkeet.takeup.users.UserActivity;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import me.relex.circleindicator.CircleIndicator;

public class SplashActivity extends AppCompatActivity {

    private FragmentPagerAdapter mPageAdapter;
    private String auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        FirebaseMessaging.getInstance().subscribeToTopic("global");
        FirebaseInstanceId.getInstance().getToken();

        Constants.SharedPreferenceData.initSharedPreferenceData(getSharedPreferences(Constants.SharedPreferenceData.SHAREDPREFERENCES, MODE_PRIVATE));

        if (Constants.SharedPreferenceData.getIsLoggedIn().equals("yes")) {
            auth = Constants.SharedPreferenceData.getAUTH();
            forward();
        }

        ViewPager viewpager = (ViewPager) findViewById(R.id.view_pager);
        mPageAdapter = new ImageFragmentAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mPageAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);
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

    public static class ImageFragmentAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public ImageFragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ImageFragment.newInstance(R.drawable.one);
                case 1:
                    return ImageFragment.newInstance(R.drawable.two);
                case 2:
                    return ImageFragment.newInstance(R.drawable.three);
                case 3:
                    return new LoginFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

}
