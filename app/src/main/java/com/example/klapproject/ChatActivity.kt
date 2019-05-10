package com.example.klapproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import android.widget.Toast
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class ChatActivity : AppCompatActivity() {

    lateinit var chatList:ArrayList<Chat> // 채팅 내용 리스트
    lateinit var dList:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
    }

    fun init(){
        val i = intent
        val id = i.getStringExtra("userId")
        initData(id)
        addListener()
    }

    fun initData(id:String){
        //파이어베이스에서 아이디에따라 대화 내용 가져오기
        dList = arrayOf("신고하기")
        userImg.setImageResource(resources.getIdentifier("chat_user", "drawable", packageName))
        userId.text = id
        itemImg.setImageResource(resources.getIdentifier("pencil", "drawable", packageName))
        chatList = ArrayList()
        chatList.add(Chat(resources.getIdentifier("chat_user", "drawable", packageName), id, "안녕하세요", "오전 9:30", false))
        chatList.add(Chat(resources.getIdentifier("chat_user", "drawable", packageName), id, "물건 사실건가요??", "오전 10:30", true))
        chatList.add(Chat(resources.getIdentifier("chat_user", "drawable", packageName), id, "네 구매하고싶어요", "오전 10:40", false))
    }

    fun addListener(){
        listView.adapter = ChatAdapter(applicationContext, R.layout.chat_row, chatList)
        sendBtn.setOnClickListener {
            bottomLinear.visibility = View.GONE
            val content = editText.text
            //파이어베이스에 추가
            //파이어베이스 변경사항에따라 화면에 chatList에 데이터 추가
        }
        btnImg1.setOnClickListener {

        }
        btnImg2.setOnClickListener {

        }
        btnImg3.setOnClickListener {

        }
        menuBtn.setOnClickListener {
            bottomLinear.visibility = View.VISIBLE
        }
        val builder = AlertDialog.Builder(this)
        builder.setItems(dList){ dialog, pos ->
            if(pos == 0){ // 신고하기인 경우
                Toast.makeText(applicationContext, "신고ㄱㄱ", Toast.LENGTH_SHORT).show()
            }
        }
        userImg.setOnClickListener {
            builder.show()
        }
        editText.setOnClickListener {
            bottomLinear.visibility = View.GONE
        }
    }

}
