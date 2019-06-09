package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_load.*
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_chat.*
import com.google.firebase.database.ChildEventListener

lateinit var MY_ID:String
lateinit var MY_NICK:String

class MainLoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_load)



        val handler = Handler()
        handler.postDelayed(Runnable {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

            finish() }, 2000)
    }

}

