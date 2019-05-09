package com.example.klapproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import kotlinx.android.synthetic.main.fragment_fragment3.*

class Fragment3 : Fragment() {

    lateinit var chatList:ArrayList<ChatUser>
    lateinit var adapter:ChatAdapter
    lateinit var listView: ListView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fragment3, container, false)
        listView = view.findViewById(R.id.listView) as ListView
        init()
        return view
    }

    fun init(){
        chatList = ArrayList<ChatUser>()
        chatList.add(ChatUser("seoyoung", "안녕하세요", resources.getIdentifier("presence_online", "drawable", activity?.packageName), resources.getIdentifier("sym_def_app_icon", "drawable", activity?.packageName)))
        adapter = ChatAdapter(activity!!.applicationContext, R.layout.room_row, chatList)
        listView.adapter = adapter
    }
}