package com.example.klapproject

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class Fragment3 : Fragment() {

    lateinit var chatList:ArrayList<ChatUser>
    lateinit var adapter:ChatRoomAdapter
    lateinit var listView: ListView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fragment3, container, false)
        listView = view.findViewById(R.id.listView) as ListView
        init()
        return view
    }

    fun init(){
        chatList = ArrayList<ChatUser>()
        chatList.add(0, ChatUser("seoyoung", "안녕하세요", resources.getIdentifier("chat_user", "drawable", activity?.packageName), resources.getIdentifier("pencil", "drawable", activity?.packageName)))
        chatList.add(0, ChatUser("seohee", "판매완료됬나요?", resources.getIdentifier("chat_user", "drawable", activity?.packageName), resources.getIdentifier("pencil", "drawable", activity?.packageName)))
        chatList.add(0, ChatUser("hyunsoo", "물건 사고싶습니다", resources.getIdentifier("chat_user", "drawable", activity?.packageName), resources.getIdentifier("pencil", "drawable", activity?.packageName)))
        chatList.add(0, ChatUser("hyunjung", "불떡 같이 먹어요", resources.getIdentifier("chat_user", "drawable", activity?.packageName), resources.getIdentifier("pencil", "drawable", activity?.packageName)))
        chatList.add(0, ChatUser("seungyeon", "문의드려요", resources.getIdentifier("chat_user", "drawable", activity?.packageName), resources.getIdentifier("pencil", "drawable", activity?.packageName)))
        chatList.add(0, ChatUser("hyojin", "부리또 배달 같이해요", resources.getIdentifier("chat_user", "drawable", activity?.packageName), resources.getIdentifier("pencil", "drawable", activity?.packageName)))
        adapter = ChatRoomAdapter(activity!!.applicationContext, R.layout.room_row, chatList)
        listView.adapter = adapter
        addListener()
    }

    fun addListener(){
        listView.setOnItemClickListener{ parent, view, position, it ->
            val w = parent.getItemAtPosition(position) as ChatUser
            val i = Intent(parent.context, ChatActivity::class.java)
            i.putExtra("userId", w.id)
            startActivity(i)
        }
    }

}