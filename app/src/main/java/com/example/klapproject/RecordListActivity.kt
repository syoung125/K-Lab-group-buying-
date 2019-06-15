package com.example.klapproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_alarm_list.*
import kotlinx.android.synthetic.main.activity_record_list.*

class RecordListActivity : AppCompatActivity() {

    var data = mutableListOf<String>()
    var info = mutableListOf<RecordData>()
    var adapter:RecordAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        transPrint()
    }

    fun transPrint() {
        val database = FirebaseDatabase.getInstance()
        val tran_list = database.getReference("user/$MY_ID/tran_list")
        tran_list.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(trans in p0.children) {
                    val trans = trans.value.toString() // 자신의 거래 내역 불러옴
                    val p_db = database.getReference("post")
                    p_db.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p1: DatabaseError) {
                        }

                        override fun onDataChange(p1: DataSnapshot) {
                            for(post_id in p1.children) { // 모든 게시물 불러옴
                                if(trans == post_id.key) { // 모든 게시물 안에서 내 거래 내역을 찾음
                                    println(post_id.child("title").value)
                                    info.add(
                                        RecordData(
                                            post_id.child("title").value.toString(),
                                            post_id.child("time").value.toString()
                                        )
                                    )
                                }
                            }
                            initAdapter()
                        }
                    })
                }
            }
        })
    }

    fun initAdapter() {
        //레이아웃을 관리하는 매니저 객체가 필요

        val layoutManager = GridLayoutManager(this,2)
        // Context 정보, 수평수직 정보, 순서 정보
        record_list.layoutManager = layoutManager
        // recyclerView를 위한 매니저이므로, 붙여줌

        adapter = RecordAdapter(info)
        record_list.adapter = adapter //data 정보를 갖는 어댑터를 생성하여 붙여줌
    }
}
