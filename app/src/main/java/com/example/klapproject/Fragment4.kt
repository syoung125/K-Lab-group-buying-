package com.example.klapproject

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.android.synthetic.main.fragment_fragment4.*

class Fragment4 : Fragment() {

    var data = mutableListOf<Int>()
    var textView = mutableListOf<TextView>()
    var data2 =  mutableListOf<String>()
    var info =  mutableListOf<RecordData>()
    lateinit var adapter:RecordAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fragment4, container, false)

        textView.add(v.findViewById(R.id.review1_count))
        textView.add(v.findViewById(R.id.review2_count))
        textView.add(v.findViewById(R.id.review3_count))
        data.add(0)
        data.add(0)
        data.add(0)
        load()

        v.findViewById<Button>(R.id.product_notice).setOnClickListener {
            var i = Intent(activity?.applicationContext, AlarmListActivity::class.java)
            i.putExtra("user",MainActivity.u_num)
            startActivity(i)
        }
        v.findViewById<Button>(R.id.record_more_btn).setOnClickListener {
            val i = Intent(activity?.applicationContext,RecordListActivity::class.java)
            i.putExtra("user",MainActivity.u_num)
            startActivity(i)
        }
        return v
    }

    fun load(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val a_list = dataSnapshot.child(MainActivity.u_num.toString()).child("review")
                for (k in a_list.children) {
                    val value = k.child("content").value.toString()
                    data.set(value.toInt(),data[value.toInt()]+1)
                }
                setCount()
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        load1()
    }

    fun setCount(){
        for(k in 0..2)
            textView[k].text = data[k].toString() + "명"
    }


    fun load1(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val a_list = dataSnapshot.child(MainActivity.u_num.toString()).child("tran_list")
                var i = 0
                for (k in a_list.children) {
                    Log.e("리코드",k.toString())
                    data2.add(k.child("post_id").value.toString())
                    i++
                    if(i == 2)
                        break
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

                    if (k.key.toString() == data2[v]) {
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

        val layoutManager = GridLayoutManager(activity!!.applicationContext,2)
        // Context 정보, 수평수직 정보, 순서 정보
        pre_record.layoutManager = layoutManager
        // recyclerView를 위한 매니저이므로, 붙여줌

        adapter = RecordAdapter(info)
        pre_record.adapter = adapter //data 정보를 갖는 어댑터를 생성하여 붙여줌
    }
}
