package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_document_detail.*
import android.R.array



class DocumentDetailActivity : AppCompatActivity() {
    var data:Document? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_detail)
        val i = intent
        data = i.getSerializableExtra("mydata") as Document
        Toast.makeText(this,"${data!!.d_postnickname}",Toast.LENGTH_LONG).show()
        datainit()
        dip_btn.setOnClickListener {
            Toast.makeText(this, "채팅방들어가!", Toast.LENGTH_LONG).show()
        }
    }

    fun datainit(){
        Ion.with(iv_img).load(data!!.d_storageFileName)
        val ctgarr:Array<String> = resources.getStringArray(R.array.spin_arr)
        tv_ctg.text = ctgarr[data!!.d_category!!.toInt()+1]
        tv_title.text= data!!.d_title
        var on=data!!.d_on
        var off=data!!.d_off
        var t_str:String = ""
        if(on) t_str += "온라인"
        if(on && off) t_str += "/"
        if(off) t_str += "오프라인"
        btn_link.text = t_str
        tv_price.text = data!!.d_price + "원"
        tv_place.text = "거래장소: "+data!!.d_place
        tv_detail.text = data!!.d_info
        //채팅 참여하는 인원정보 알ㄹ려면 array 정보도 받아야함
        tv_totalnum.text = data!!.d_num
        tv_currentnum.text = data!!.d_chatlist.size.toString()
    }

}
