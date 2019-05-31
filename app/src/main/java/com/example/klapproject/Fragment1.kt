package com.example.klapproject

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.klapproject.MainActivity.Companion.doc_list
import com.example.klapproject.MainActivity.Companion.u_nickname
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_fragment1.*
import kotlinx.android.synthetic.main.fragment_fragment1.view.*
import java.util.*

class Fragment1 : Fragment() {
    lateinit var adapter:CategoryAdapter
    lateinit var rview:RecyclerView
    var show_list:MutableList<Document> = mutableListOf()
    var mchoice:Int = 0
    var fdb = FirebaseDatabase.getInstance().getReference("post")
    val TAG = "Fragment1_debug"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)
        rview = view.findViewById<View>(R.id.category_list) as RecyclerView
        initData(0)

        view.imgButton0.setOnClickListener {
            initData(0)
        }
        view.imgButton1.setOnClickListener {
            initData(1)
        }
        view.imgButton2.setOnClickListener {
            initData(2)
        }
        view.imgButton3.setOnClickListener {
            initData(3)
        }
        view.imgButton4.setOnClickListener {
            initData(4)
        }
        view.imgButton5.setOnClickListener {
            initData(5)
        }
        view.imgButton6.setOnClickListener {
            initData(6)
        }
        view.imgButton7.setOnClickListener {
            initData(7)
        }
        view.f1_btn.setOnClickListener {
            if(f1_ctg.visibility == View.VISIBLE){
                f1_ctg.visibility = View.GONE
                f1_btn.setImageResource(R.drawable.down_chevron)
            }else if(f1_ctg.visibility == View.GONE){
                f1_ctg.visibility = View.VISIBLE
                f1_btn.setImageResource(R.drawable.up_chevron)
            }
        }
//        if(!show_list.isEmpty()){
//            Log.v(TAG, "show_list 차있음")
//
//
//        }else{
//            Log.v(TAG, "show_list 비었음")
//        }
//        initData()
        return view
    }
    fun initData(mchoice:Int){
        Log.v(TAG, "데이터를 가져오자")
            fdb.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(d: DataSnapshot) {
                    Log.v(TAG, "onDataChange")
                    show_list.clear()
                    for (i in d.children) {
                        val index = i.key.toString()
                        Log.v(TAG, i.toString()+" 갖고오는거 맞냐"+index)
                        var td: Document = Document(u_nickname)
                        td.d_category = d.child(index).child("category").value.toString()
                        if(mchoice != 0 && td.d_category != (mchoice-1).toString())
                            continue
                        td.d_url = d.child(index).child("uri").value.toString()
                        td.d_title = d.child(index).child("title").value.toString()
                        td.d_on = d.child(index).child("on").value.toString().toBoolean()
                        td.d_off = d.child(index).child("off").value.toString().toBoolean()
                        td.d_market = d.child(index).child("link").value.toString()
                        td.d_info = d.child(index).child("info").value.toString()
                        td.d_num = d.child(index).child("num").value.toString()
                        td.d_price = d.child(index).child("price").value.toString()
                        td.d_place = d.child(index).child("place").value.toString()
                        td.d_duty = d.child(index).child("duty").value.toString()
                        show_list.add(td)
                    }
                    adapter.notifyDataSetChanged()
                }

            })
        initLayout()
    }


    fun initLayout(){
        var layoutManager= GridLayoutManager(this.requireContext(),2)
        rview.layoutManager = layoutManager
        adapter = CategoryAdapter(show_list)
        rview.adapter = adapter

        adapter.itemClickListener = object : CategoryAdapter.OnItemClickListener{
            override fun OnItemClick(holder: CategoryAdapter.ViewHolder, view: View, data: Document, position: Int) {
                Toast.makeText(context, data.d_title, Toast.LENGTH_SHORT).show()
                //this->interface를 의히마니까 applicationContext로 해줌
                //특정 포지션 정보가 필요하면 이런식으로 만들어 줘아함!
            }
        }

    }


}
