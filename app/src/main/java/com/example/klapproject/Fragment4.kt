package com.example.klapproject

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_fragment4.*

class Fragment4 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fragment4, container, false)
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

}
