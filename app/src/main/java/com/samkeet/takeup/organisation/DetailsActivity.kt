package com.samkeet.takeup.organisation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.samkeet.takeup.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details2.*
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by knightshade on 10/19/17.
 */
class DetailsActivity : AppCompatActivity() {

    lateinit var mTreeObject: JSONObject
    lateinit var name: String
    lateinit var details: String
    lateinit var status: String
    lateinit var user: String
    lateinit var addedname: String
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        try {
            mTreeObject = JSONObject(intent.getStringExtra("DATA"))
            name = mTreeObject.getString("Plant_Name")
            details = mTreeObject.getString("Plant_Details")
            status = mTreeObject.getString("Take_Up_Status")
            user = mTreeObject.getString("Take_Up_User")
            addedname = mTreeObject.getString("User_Name")
            url = mTreeObject.getString("Image_Url")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        tv_plant_name.text = name
        tv_plant_details.text = details
        tv_plant_take_up_status.text = status
        tv_plant_take_up_user.text = user
        tv_user_added_name.text = addedname
        Picasso.with(applicationContext).load(url).into(iv_plant_image)
    }
}
