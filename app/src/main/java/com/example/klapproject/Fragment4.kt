package com.example.klapproject

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_fragment4.*

class Fragment4 : Fragment() {

    var data = mutableListOf<Int>()
    var textView = mutableListOf<TextView>()

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
    }

    fun setCount(){
        for(k in 0..2)
            textView[k].text = data[k].toString() + "명"
    }

}
