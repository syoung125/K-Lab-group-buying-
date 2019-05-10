package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_load.*

class MainLoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_load)
        temp_start_button.setOnClickListener {
            var longinintent = Intent(this, LoginActivity::class.java)
            startActivity(longinintent)
        }
    }
}
