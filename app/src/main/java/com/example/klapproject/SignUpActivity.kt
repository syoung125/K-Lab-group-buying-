package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        loginbutton.setOnClickListener {
            var longinintent = Intent(this, LoginActivity::class.java)
            startActivity(longinintent)
        }
    }
}
