package com.example.klapproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_fragment1.view.*
import java.util.*

class Fragment1 : Fragment() {
    lateinit var adapter:CategoryAdapter
    lateinit var rview:RecyclerView
//    var division:Int = -1
//
//    interface makeIntent{
//        fun makeIntent(num:Int);
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)
        rview = view.findViewById<View>(R.id.category_list) as RecyclerView
//        view.imgButton1.setOnClickListener {
//            if(activity is makeIntent)
//                (activity as makeIntent).makeIntent(1)
//        }
//        view.imgButton2.setOnClickListener {
//            if(activity is makeIntent)
//                (activity as makeIntent).makeIntent(2)
//        }
//        view.imgButton3.setOnClickListener {
//            if(activity is makeIntent)
//                (activity as makeIntent).makeIntent(3)
//        }
//        view.imgButton4.setOnClickListener {
//            if(activity is makeIntent)
//                (activity as makeIntent).makeIntent(4)
//        }
//        view.imgButton5.setOnClickListener {
//            if(activity is makeIntent)
//                (activity as makeIntent).makeIntent(5)
//        }
//        view.imgButton6.setOnClickListener {
//            if(activity is makeIntent)
//                (activity as makeIntent).makeIntent(6)
//        }
        if(!doc_list.isEmpty()){
            initLayout()
        }
        return view
    }

    fun initLayout(){
        var layoutManager= GridLayoutManager(this.requireContext(),2)
        rview.layoutManager = layoutManager
        adapter = CategoryAdapter(doc_list)
        rview.adapter = adapter

        adapter.itemClickListener = object : CategoryAdapter.OnItemClickListener{
            override fun OnItemClick(holder: CategoryAdapter.ViewHolder, view: View, data: Document, position: Int) {
                Toast.makeText(context, data.d_title, Toast.LENGTH_SHORT).show()
                //this->interface를 의히마니까 applicationContext로 해줌
                //특정 포지션 정보가 필요하면 이런식으로 만들어 줘아ㅑ함!
            }
        }

    }


}
