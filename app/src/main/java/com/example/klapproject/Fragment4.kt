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
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.android.synthetic.main.fragment_fragment4.*
import kotlinx.android.synthetic.main.fragment_fragment4.view.*
import java.nio.file.Files.size


class Fragment4 : Fragment() {

    var data = mutableListOf<Int>()
    var count_textView = mutableListOf<TextView>()
    var review_textView = mutableListOf<TextView>()
    var data2 = mutableListOf<String>()
    var info = mutableListOf<RecordData>()
    lateinit var adapter: RecordAdapter
    lateinit var v:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_fragment4, container, false)

        review_textView.add(v.findViewById(R.id.review1))
        review_textView.add(v.findViewById(R.id.review2))
        review_textView.add(v.findViewById(R.id.review3))
        count_textView.add(v.findViewById(R.id.review1_count))
        count_textView.add(v.findViewById(R.id.review2_count))
        count_textView.add(v.findViewById(R.id.review3_count))
        data.add(0)
        data.add(0)
        data.add(0)

        reviewPrint()
        transPrint()

        v.findViewById<ImageButton>(R.id.product_notice).setOnClickListener {
            var i = Intent(activity!!.applicationContext, AlarmListActivity::class.java)
            //           i.putExtra("user",MainActivity.u_num)
            startActivity(i)
        }
        v.findViewById<ImageButton>(R.id.record_more_btn).setOnClickListener {
            val i = Intent(activity!!.applicationContext, RecordListActivity::class.java)
            //           i.putExtra("user",MainActivity.u_num)
            startActivity(i)
        }
        v.user_name.setText(MY_ID)
        return v
    }

    fun reviewPrint() {
        var review_array = resources.getStringArray(R.array.review_arr)
        var review_count = arrayOf(0, 0, 0, 0, 0, 0)
        println(review_array)

        val database = FirebaseDatabase.getInstance()
        val review_list = database.getReference("user/$MY_ID/review")
        review_list.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p1: DatabaseError) {
            }

            override fun onDataChange(p1: DataSnapshot) {
                for (i in p1.children) {
                    val review = i.getValue().toString()
                    for (i in 0 until review_array.size) {
                        if (review == review_array[i]) {
                            review_count[i]++
                        }
                    }
                }

                var sort_index = arrayOf(0, 1, 2, 3, 4, 5)

                // count 큰순으로 정렬
                for (i in 0 until review_count.size - 1) {
                    for (j in i + 1 until review_count.size) {
                        if (review_count[i] < review_count[j]) {
                            var temp = sort_index[i]
                            sort_index[i] = sort_index[j]
                            sort_index[j] = temp
                        }
                    }
                }


                for (i in 0..2) {
                    if (review_count[sort_index[i]] == 0)
                        break
                    review_textView[i].text = (i + 1).toString() + ". " + review_array[sort_index[i]]
                    count_textView[i].text = review_count[sort_index[i]].toString() + "명"
                }
            }
        })
    }

    fun transPrint() {
        val database = FirebaseDatabase.getInstance()
        val tran_list = database.getReference("user/$MY_ID/tran_list")
        tran_list.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(trans in p0.children) {
                    info.clear()

                    v.t_count.text = "거래횟수 : " + p0.childrenCount.toString()+" 번"
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
                                            post_id.child("time").value.toString(),
                                            post_id.child("sFileName").value.toString()
                                        )
                                    )
                                    if(info.size == 2) break;
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

        val layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
        // Context 정보, 수평수직 정보, 순서 정보
        pre_record.layoutManager = layoutManager
        // recyclerView를 위한 매니저이므로, 붙여줌

        adapter = RecordAdapter(info)
        pre_record.adapter = adapter //data 정보를 갖는 어댑터를 생성하여 붙여줌
    }

}