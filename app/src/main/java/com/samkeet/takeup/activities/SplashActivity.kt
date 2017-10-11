package com.samkeet.takeup.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.samkeet.takeup.Constants
import com.samkeet.takeup.R
import com.samkeet.takeup.fragments.ImageFragment
import com.samkeet.takeup.fragments.LoginFragment
import com.samkeet.takeup.organisation.OrgActivity
import com.samkeet.takeup.users.UserActivity

import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

import me.relex.circleindicator.CircleIndicator

class SplashActivity : AppCompatActivity() {

    private var mPageAdapter: FragmentPagerAdapter? = null
    private var auth: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        FirebaseMessaging.getInstance().subscribeToTopic("global")
        FirebaseInstanceId.getInstance().token

        Constants.SharedPreferenceData.initSharedPreferenceData(getSharedPreferences(Constants.SharedPreferenceData.SHAREDPREFERENCES, Context.MODE_PRIVATE))

        if (Constants.SharedPreferenceData.getIsLoggedIn() == "yes") {
            auth = Constants.SharedPreferenceData.getAUTH()
            forward()
        }

        val viewpager = findViewById(R.id.view_pager) as ViewPager
        mPageAdapter = ImageFragmentAdapter(supportFragmentManager)
        viewpager.adapter = mPageAdapter
        val indicator = findViewById(R.id.indicator) as CircleIndicator
        indicator.setViewPager(viewpager)
    }

    fun forward() {
        if (auth == "ORG") {
            val intent = Intent(applicationContext, OrgActivity::class.java)
            startActivity(intent)
            finish()
        } else if (auth == "USER") {
            val intent = Intent(applicationContext, UserActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(applicationContext, "USER_TYPE mismatch", Toast.LENGTH_SHORT).show()
            Constants.SharedPreferenceData.clearData()
        }
    }

    class ImageFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return ImageFragment.newInstance(R.drawable.one)
                1 -> return ImageFragment.newInstance(R.drawable.two)
                2 -> return ImageFragment.newInstance(R.drawable.three)
                3 -> return LoginFragment()
                else -> return null
            }
        }

        override fun getCount(): Int {
            return NUM_ITEMS
        }

        companion object {
            private val NUM_ITEMS = 4
        }
    }

}
