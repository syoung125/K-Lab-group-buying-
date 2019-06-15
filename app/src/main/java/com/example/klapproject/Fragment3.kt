package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class Fragment3 : Fragment() {

    lateinit var chatList:ArrayList<ChatRoom>
    lateinit var adapter:ChatRoomAdapter
    lateinit var listView: ListView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fragment3, container, false)
        listView = view.findViewById<ListView>(R.id.listView)
        init()
        return view
    }

    fun init(){
        initData()
        addListener()
    }

    fun initData(){
        chatList = ArrayList<ChatRoom>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("chat")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chatList.clear()
                chatList = ArrayList<ChatRoom>()
                var flag = false // 디비에 존재하는 모든 채팅방중 내가 들어가 있으면 true
                var memberList:ArrayList<String> //room에 존재하는 member 리스트
                for (i in dataSnapshot.children) { //방 확인
                    flag = false
                    memberList = ArrayList()
                    memberList.clear()
                    val index = i.key.toString() //room index
                    val room_id = index
                    for(j in dataSnapshot.child(index).child("member_id").children) { //member_id 확인
                        val id = dataSnapshot.child(index).child("member_id").child(j.key.toString()).value.toString()
                        if(id == MY_ID){
                            flag = true
                        }
                        else{
                            memberList.add(id)
                        }
                    }
                    if(flag && memberList.isNotEmpty()){ //room에 나 포함
                        chatList.add(0, ChatRoom(room_id, memberList))
                    }
                }
                if(activity != null)
                    adapter = ChatRoomAdapter(activity!!.applicationContext, R.layout.room_row, chatList)
                listView.adapter = adapter
            }
        })
    }


    fun addListener(){
        listView.setOnItemClickListener{ parent, view, position, it ->
            val w = parent.getItemAtPosition(position) as ChatRoom
            val i = Intent(parent.context, ChatActivity::class.java)
            i.putExtra("roomId", w.room_id) //채팅방 고유번호 넘김(기본키)
            i.putExtra("memberList", w.memberList)
            startActivity(i)
        }
    }

}