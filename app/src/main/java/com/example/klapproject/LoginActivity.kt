package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ChildEventListener


class LoginActivity : AppCompatActivity() {
    var flag = 0
    override fun onBackPressed() { //super.onBackPressed()
        val i = Intent()
        setResult(Activity.RESULT_CANCELED,i)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signin_button.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("user")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var check = 0
                    if (MY_ID == null) {
                        for (i in dataSnapshot.children) {
                            val index = i.key.toString()
                            val id = dataSnapshot.child(index).child("id").value.toString()
                            val pw = dataSnapshot.child(index).child("password").value.toString()

                            if (id == editText_id.text.toString() && pw == editText_pw.text.toString()) {
                                MY_ID = id
                                MY_NICK = dataSnapshot.child(index).child("name").value.toString()
                                flag = 1
                                val i = Intent()
                                i.putExtra("user", check)
//                            i.putExtra("userid", id)
                                i.putExtra("nick", dataSnapshot.child(index).child("name").value.toString())
                                setResult(Activity.RESULT_OK, i)
//                            finish()
                                var mainPage = Intent(applicationContext, MainActivity::class.java)
                                startActivityForResult(mainPage, 1)
                            }
                            check++
                        }
                        if (flag == 0)
                            Toast.makeText(applicationContext, "아이디 또는 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }

        signup_button.setOnClickListener {
            var signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }
}