package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : Activity() {

//    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        joinbutton.setOnClickListener {
            var longinintent = Intent(this, LoginActivity::class.java)
            startActivity(longinintent)
        }
        init()
    }
    fun init(){
        su_sendmail.setOnClickListener {

        }
    }

}
