package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import com.google.firebase.database.DatabaseReference


class SignUpActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    fun init() {
        //버튼이벤트
        btnCreateAccount!!.setOnClickListener {
            //회원가입
            val userID = ET_su_email.text.toString()
            val userPW = ET_su_pw.text.toString()
            val insert = FirebaseDatabase.getInstance().getReference("user").push()
            insert.child("id").setValue(userID)
            insert.child("password").setValue(userPW)

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}


