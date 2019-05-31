package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : Activity() {




    var a:Boolean = true
    var b:Boolean = true
    var c:Boolean = true
    var d:Boolean = true
    var identiNum = -1
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setBtn()
    }

    fun allCheck():Boolean{
        if(a && b && c && d)
            return true
        else{
            Toast.makeText(this,"회원 가입 절차를 모두 완료해주세요",Toast.LENGTH_SHORT).show()
            return false
        }
    }

    fun setBtn() {
        //버튼이벤트
        btnCreateAccount!!.setOnClickListener {
            //회원가입
            Log.e("SignUp","회원가입 시도")
            if (allCheck()) {
                // 모든 조건 충족
                val userID = ET_su_email.text.toString()
                val userPW = ET_su_pw.text.toString()
                val insert = FirebaseDatabase.getInstance().getReference("user").push()
                insert.child("id").setValue(userID)
                insert.child("password").setValue(userPW)

                Log.e("SignUp","회원가입 성공")

                finish()
            }
        }
        su_sendmail.setOnClickListener{
            // 인증메일 발송
            Log.e("SignUp","인증메일 발송")

            ET_su_email.text
            identiNum = (1000..9999).random()
            sendMail()
            Toast.makeText(this,"해당 메일로 인증번호가 발송되었습니다",Toast.LENGTH_SHORT).show()
        }

        su_checkmail.setOnClickListener {
            // 인증 번호 확인
            ET_num.text
            Log.e("SignUp","인증 시도")
            Toast.makeText(this,"인증에 성공하였습니다",Toast.LENGTH_SHORT).show()
        }

        su_checkduplicate.setOnClickListener{
            // 닉네임 중복 확인
            Log.e("SignUp","닉네임 중복확인")
            ET_nickname.text
        }

        su_checkLocation.setOnClickListener {
            // 위치 인증
            Log.e("SignUp","위치 인증")
        }
    }

    fun sendMail(){

        var email =  ET_su_email.text.toString()
        var password = ET_su_pw.text.toString()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    Toast.makeText(this, "인증 성공", Toast.LENGTH_SHORT).show()
                } else {
                    // 회원가입 실패
                    Toast.makeText(this, "인증 실패", Toast.LENGTH_SHORT).show()
                }
            })
    }
}


