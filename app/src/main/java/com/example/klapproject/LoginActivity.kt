package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signin_button.setOnClickListener {
                var gohomeIntent = Intent(this, MainActivity::class.java)
                startActivity(gohomeIntent)
        }
        signup_button.setOnClickListener {
            var signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }
}
