package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.AdapterView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_alarm_list.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.room_row.*

class AlarmListActivity : AppCompatActivity() {

    var adapter:AlarmAdapter?=null
    val database = FirebaseDatabase.getInstance()
    var data = mutableListOf<String>()
    var USER_NAME:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_list)
        USER_NAME = intent.getIntExtra("user",-1)
        Log.e("알람리스트","$USER_NAME")
        load()
    }

    fun load(){
        val myRef = database.getReference("user")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val a_list = dataSnapshot.child(USER_NAME.toString()).child("alarm_list")
                Log.e("알람리스트","$dataSnapshot")
                val c = dataSnapshot.child(USER_NAME.toString())
                Log.e("알람리스트","$c")
                Log.e("알람리스트","$a_list")
                for (k in a_list.children) {
                    data.add(k.child("item_name").value.toString())
                }
                initAdapter()
                Log.e("알람리스트","어댑터까지 붙임")
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun initAdapter() {
        //레이아웃을 관리하는 매니저 객체가 필요

        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        // Context 정보, 수평수직 정보, 순서 정보
        alarm_list.layoutManager = layoutManager
        // recyclerView를 위한 매니저이므로, 붙여줌

        adapter = AlarmAdapter(data)
        alarm_list.adapter = adapter //data 정보를 갖는 어댑터를 생성하여 붙여줌

        initSwipe()
    }

    fun initSwipe(){
        val simpleItemTouchCallback
                = object :ItemTouchHelper.SimpleCallback( // 오브젝트 이동 리스너
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {

                adapter!!.moveItem(viewHolder.adapterPosition,target.adapterPosition)
                // 어댑터에 정의된 이동함수
                // 뷰 홀더가 어댑터 개체 정보를 갖고있고, 위치정보를 받아올 수 있음
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter!!.removeItem(viewHolder.adapterPosition)

                
                // 어댑터에 정의된 삭제함수
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        // 위에서 선언한 행동 클래스를 수행할 헬퍼객체를 생성
        itemTouchHelper.attachToRecyclerView(alarm_list)
        // 해당 세팅이 적용되길 원하는 리사이클러 뷰에 붙여줌
    }
    fun initBtn(){
        add_alarm.setOnClickListener {

        }
    }
}
