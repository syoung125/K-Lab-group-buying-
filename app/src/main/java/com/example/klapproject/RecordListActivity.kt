package com.example.klapproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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

        load1()
    }

    fun load1(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val a_list = dataSnapshot.child(MainActivity.u_num.toString()).child("tran_list")
                for (k in a_list.children) {
                    Log.e("리코드",k.toString())
                    data.add(k.child("post_id").value.toString())
                }
                load2(database)
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun load2(database:FirebaseDatabase){
        val myRef = database.getReference("post")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var v=0
                for (k in dataSnapshot.children) {
                    Log.e("load2", k.toString())

                    if (k.key.toString() == data[v]) {
                        info.add(
                            RecordData(
                                k.child("title").value.toString(),
                                k.child("category").value.toString().toInt()
                            )
                        )
                        v++
                        if(v == data.size)
                            break;
                    }
                }
                initAdapter()
            }
            override fun onCancelled(databaseError: DatabaseError) {

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
