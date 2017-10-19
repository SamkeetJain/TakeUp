package com.samkeet.takeup

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by knightshade on 10/19/17.
 */
class DevelopersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)
    }

    fun BackButton(v: View) {
        finish()
    }

    fun samcall(v: View) {
        try {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + "+918147514179")
            startActivity(callIntent)
        } catch (activityException: ActivityNotFoundException) {
        }

    }

    fun samemail(v: View) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "jain.sankeet2210@gmail.com", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From Smart Reva Android App")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    fun vaicall(v: View) {
        try {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + "+919686488775")
            startActivity(callIntent)
        } catch (activityException: ActivityNotFoundException) {
        }

    }

    fun vaiemail(v: View) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "vaibhavkrishna.bhosle@gmail.com", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From Smart Reva Android App")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    fun leecall(v: View) {
        try {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + "+919740504214")
            startActivity(callIntent)
        } catch (activityException: ActivityNotFoundException) {
        }

    }

    fun leeemail(v: View) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "lilashsah2012@gmail.com", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From Smart Reva Android App")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    fun radzcall(v: View) {
        try {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + "+919902476427")
            startActivity(callIntent)
        } catch (activityException: ActivityNotFoundException) {
        }

    }

    fun radzemail(v: View) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "gargradhika1996@gmail.com", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From Smart Reva Android App")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
}