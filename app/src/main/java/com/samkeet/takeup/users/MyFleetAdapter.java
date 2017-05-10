package com.samkeet.takeup.users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.takeup.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by leelash on 10-05-2017.
 */

public class MyFleetAdapter extends RecyclerView.Adapter<MyFleetAdapter.ViewHolder> {

    public JSONArray mTreeData;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName, mOrg, mStatus, mAdopterName;

        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name);
            mOrg = (TextView) v.findViewById(R.id.org);
            mStatus = (TextView) v.findViewById(R.id.status);
            mAdopterName = (TextView) v.findViewById(R.id.adopter_name);
        }
    }

    public MyFleetAdapter(JSONArray mTreeData) {
        this.mTreeData = mTreeData;

    }

    @Override
    public MyFleetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_my_fleet, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String name, org, status, a_name;

        try {
            name = mTreeData.getJSONObject(position).getString("Plant_Name");
            org = "Organisation : " + mTreeData.getJSONObject(position).getString("User_Name");
            status = "Adopted Status : " + mTreeData.getJSONObject(position).getString("Take_Up_Status");
            a_name = "Adopter Name : " + mTreeData.getJSONObject(position).getString("Take_Up_User");

            holder.mName.setText(name);
            holder.mOrg.setText(org);
            holder.mStatus.setText(status);
            holder.mAdopterName.setText(a_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mTreeData.length();
    }

}
