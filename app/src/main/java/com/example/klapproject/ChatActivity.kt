package com.example.klapproject

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import android.widget.Toast
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.koushikdutta.ion.Ion
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import java.nio.file.Files.size
import java.nio.file.Files.size


class ChatActivity : AppCompatActivity() {

    lateinit var chatList: ArrayList<Chat> // 채팅 내용 리스트
    lateinit var dList: Array<String>
    var id: String = ""
    lateinit var memberList: ArrayList<String>
    var rBuilder: AlertDialog.Builder? = null
    lateinit var memberArray: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
    }

    fun init() {
        val i = intent
        id = i.getStringExtra("roomId")
        memberList = ArrayList()
        initData(id)
        addListener()
    }

    fun initData(id: String) {
        //파이어베이스에서 아이디에따라 대화 내용 가져오기
        dList = arrayOf("신고하기")
        chatList = ArrayList<Chat>()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("chat").child(id).child("chat_list")
        val myRef2 = database.getReference("chat").child(id).child("member_id")
        val myRef3 = database.getReference("chat").child(id).child("post_id")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chatList.clear()
                chatList = ArrayList<Chat>()
                for (i in dataSnapshot.children) { //방 확인
                    val content = i.child("content").value.toString()
                    var is_me: Boolean = false
                    val userId = i.child("userId").value.toString()
                    if (userId == MY_ID)
                        is_me = true
                    val time = i.child("time").value.toString()
                    chatList.add(
                        Chat(
                            resources.getIdentifier("chat_user", "drawable", packageName),
                            userId,
                            content,
                            time,
                            is_me
                        )
                    )
                }
                listView.adapter = ChatAdapter(applicationContext, R.layout.chat_row, chatList)
                listView.setSelection(chatList.size - 1)
            }
        })
        myRef2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                memberList.clear()
                memberList = ArrayList<String>()
                var str = ""
                for (i in p0.children) {
                    if (MY_ID != i.value.toString()) {
                        memberList.add(i.value.toString())
                    }
                }
                for (i in 0..memberList.size - 1) {
                    if (i != memberList.size - 1)
                        str += memberList[i] + ", "
                    else
                        str += memberList[i]
                }
                userId.setText(str)

                memberArray = memberList.toArray(arrayOfNulls<String>(memberList.size))

            }
        })

        myRef3.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val postid = p0.value.toString()
                val myRef4 = database.getReference("post").child(postid).child("sFileName")
                myRef4.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val storageReference = FirebaseStorage.getInstance().reference
                        Ion.with(itemImg).load(p0.value.toString())
                    }
                })

            }
        })
    }

    fun addListener() {
        sendBtn.setOnClickListener {
            bottomLinear.visibility = View.GONE
            val content = editText.text.toString()
            if (content.trim() != "") {
                val time = SimpleDateFormat("HH:mm", Locale.KOREA).format(Date())
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("chat").child(id).child("chat_list")
                val newMyRef = myRef.push()
                newMyRef.setValue(
                    Chat(
                        resources.getIdentifier("chat_user", "drawable", packageName),
                        MY_ID,
                        content,
                        time,
                        true
                    )
                )
            }
            editText.setText("")
        }
        btnImg1.setOnClickListener {
            //거래 확정

        }
        btnImg2.setOnClickListener {
            //거래 완료
            val database = FirebaseDatabase.getInstance()
            val post_id = database.getReference("chat").child(id).child("post_id")
            var user_trans = database.getReference("user/$MY_ID")

            post_id.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val tran_list_check = database.getReference("user/$MY_ID/tran_list")
                    val new_post = p0.getValue().toString()
                    tran_list_check.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p1: DatabaseError) {
                        }

                        override fun onDataChange(p1: DataSnapshot) {
                            for (i in p1.children) {
                                val post = i.getValue().toString()
                                if (post == new_post) {  // 이미 거래완료 누름
                                    Toast.makeText(applicationContext, "이미 거래를 완료 하셨습니다", Toast.LENGTH_SHORT).show()
                                    return
                                }
                            }

                            var review_list: ArrayList<review> = arrayListOf()
                            var count = memberArray.size

                            for (pos in 0 until memberArray.size) {
                                val builder = AlertDialog.Builder(this@ChatActivity) //alert 다이얼로그 builder 이용해서 다이얼로그 생성
                                val addDialog = layoutInflater.inflate(R.layout.review, null)
                                val dialogMemberID = addDialog.findViewById<TextView>(R.id.member)

                                val memID = memberArray[pos]
                                dialogMemberID.setText(memID)
                                builder.setView(addDialog)
                                    .setPositiveButton("작성") { dialogInterface, i ->
                                        val dialogSpinner = addDialog.findViewById<Spinner>(R.id.review_spinner)
                                        val dialogReview = dialogSpinner.selectedItem.toString()
                                        review_list.add(review(memID, dialogReview))
                                        count--
                                        if (count == 0) { // 마지막까지 했음
                                            for (i in 0 until review_list.size) {
                                                val memID = review_list[i].id
                                                val review = review_list[i].review
                                                var user_review = FirebaseDatabase.getInstance().getReference("user/$memID/review")
                                                user_review.push().setValue(review)
                                            }
                                            // 거래내역에 추가
                                            user_trans.child("tran_list").push().setValue(new_post)
                                        }
                                    }
                                    .show()
                            }

                        }
                    })

                }
            })
        }

        menuBtn.setOnClickListener {
            bottomLinear.visibility = View.VISIBLE
        }
        val builder = AlertDialog.Builder(this)
        builder.setItems(dList) { dialog, pos ->
            if (pos == 0) { // 신고하기인 경우
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
