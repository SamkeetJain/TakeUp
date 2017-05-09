package com.samkeet.takeup.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.samkeet.takeup.R;
import com.samkeet.takeup.fragments.ImageFragment;
import com.samkeet.takeup.fragments.LoginFragment;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private FragmentPagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewpager = (ViewPager) findViewById(R.id.view_pager);
        mPageAdapter = new ImageFragmentAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mPageAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);
    }

    public static class ImageFragmentAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public ImageFragmentAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0: return ImageFragment.newInstance(R.drawable.one);
                case 1: return ImageFragment.newInstance(R.drawable.two);
                case 2: return ImageFragment.newInstance(R.drawable.three);
                case 3: return new LoginFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

}
