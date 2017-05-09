package com.samkeet.takeup.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.samkeet.takeup.R;

/**
 * Created by Hi Abhishek on 5/9/2017.
 */

public class ImageFragment extends Fragment {
    private int mResourceId;

    public static ImageFragment newInstance(int resourceId){
        ImageFragment imageFragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt("resourceId", resourceId);
        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResourceId = getArguments().getInt("resourceId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_image_indicator, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        imageView.setImageResource(mResourceId);
        return view;
    }
}
