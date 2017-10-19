package com.samkeet.takeup.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samkeet.takeup.R
import com.samkeet.takeup.activities.LoginActivity
import com.samkeet.takeup.activities.SignupActivity
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by knightshade on 10/19/17.
 */
class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        btn_login.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }

        btn_signup.setOnClickListener {
            startActivity(Intent(context, SignupActivity::class.java))
        }
    }
}