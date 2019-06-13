package com.example.klapproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import android.widget.Toast
import android.support.v7.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity() {

    lateinit var chatList:ArrayList<Chat> // 채팅 내용 리스트
    lateinit var dList:Array<String>
    var id:String = ""
    lateinit var memberList:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
    }

    fun init(){
        val i = intent
        id = i.getStringExtra("roomId")
        memberList = ArrayList()
        initData(id)
        addListener()
    }

    fun initData(id:String){
        //파이어베이스에서 아이디에따라 대화 내용 가져오기
        dList = arrayOf("신고하기")
        chatList = ArrayList<Chat>()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("chat").child(id).child("chat_list")
        val myRef2 = database.getReference("chat").child(id).child("member_id")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chatList.clear()
                chatList = ArrayList<Chat>()
                for (i in dataSnapshot.children) { //방 확인
                    val content = i.child("content").value.toString()
                    var is_me:Boolean = false
                    val userId = i.child("userId").value.toString()
                    if(userId == MY_ID)
                        is_me = true
                    val time = i.child("time").value.toString()
                    chatList.add(Chat(resources.getIdentifier("chat_user", "drawable", packageName), userId, content, time, is_me))
                }
                listView.adapter = ChatAdapter(applicationContext, R.layout.chat_row, chatList)
                listView.setSelection(chatList.size - 1)
            }
        })
        myRef2.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                memberList.clear()
                memberList = ArrayList<String>()
                var str = ""
                for (i in p0.children) {
                    if(MY_ID != i.value.toString()) {
                        memberList.add(i.value.toString())
                    }
                }
                for(i in 0..memberList.size - 1){
                    if(i != memberList.size - 1)
                        str += memberList[i] + ", "
                    else
                        str += memberList[i]
                }
                userId.setText(str)
            }
        })


//        userImg.setImageResource(resources.getIdentifier("chat_user", "drawable", packageName))
//        userId.text = id
//        itemImg.setImageResource(resources.getIdentifier("pencil", "drawable", packageName))

    }

    fun addListener(){
        sendBtn.setOnClickListener {
            bottomLinear.visibility = View.GONE
            val content = editText.text.toString()
            val time = SimpleDateFormat("HH:mm", Locale.KOREA).format(Date())
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("chat").child(id).child("chat_list")
            val newMyRef = myRef.push()
            newMyRef.setValue(Chat(resources.getIdentifier("chat_user", "drawable", packageName), MY_ID, content, time, true))
            editText.setText("")
        }
        btnImg1.setOnClickListener { //거래 확정

        }
        btnImg2.setOnClickListener { //거래 완료

        }
        val rBuilder = AlertDialog.Builder(this)
        val memberArray:Array<String> = arrayOf("")
        rBuilder.setItems(memberList.toArray(memberArray)){ dialog, pos ->
            val memberId = memberArray[pos]
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("user")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var key:String = ""
                    for (i in dataSnapshot.children) { //방 확인
                        if(i.child("id").toString() == memberId){
                            key = i.key.toString()
                            break
                        }
                    }
                    val newMyRef = myRef.child(key).child("review").push()
                    newMyRef.setValue("reviewContent")
                }
            })
        }
        btnImg3.setOnClickListener { //리뷰
            rBuilder.show()
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
        itemImg.setOnClickListener {
            builder.show()
        }
        editText.setOnClickListener {
            bottomLinear.visibility = View.GONE
        }
    }

}
