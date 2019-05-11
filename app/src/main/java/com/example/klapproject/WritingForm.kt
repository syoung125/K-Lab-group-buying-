package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_writing_form.*

class WritingForm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_form)
        init()
    }
    fun init(){
        regi_product.setOnClickListener {
            var gohomeintent = Intent(this, MainActivity::class.java)
            startActivity(gohomeintent)
        }
    }
}
