package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_add_alarm_pop_up.*
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager


class AddAlarmPopUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
        ,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_alarm_pop_up)

        alarm_OK.setOnClickListener {
            val i = Intent()
            i.putExtra("result", add_name.text.toString());
            setResult(RESULT_OK, i);

            finish();
        }
        alarm_CANCEL.setOnClickListener {
            val i = Intent()
            setResult(RESULT_CANCELED, i);

            finish();
        }

    }

    override fun onTouchEvent(event:MotionEvent):Boolean {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    override fun onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
