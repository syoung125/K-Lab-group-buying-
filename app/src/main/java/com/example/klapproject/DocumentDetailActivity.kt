package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_document_detail.*
import android.R.array
import android.view.View


class DocumentDetailActivity : AppCompatActivity() {
    var data:Document? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_detail)
        val i = intent
        data = i.getSerializableExtra("mydata") as Document
        Toast.makeText(this,"${data!!.d_postnickname}",Toast.LENGTH_LONG).show()
        datainit()
    }

    fun datainit(){
        tv_postnickname.text = data!!.d_postnickname
        Ion.with(iv_img).load(data!!.d_storageFileName)
        val ctgarr:Array<String> = resources.getStringArray(R.array.spin_arr)
        tv_ctg.text = ctgarr[data!!.d_category!!.toInt()]
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

    //채팅룸 만들기
    fun makechatroom(view: View){
        //Toast.makeText(this, "채팅방들어가!", Toast.LENGTH_LONG).show()
        if(MY_NICK == data!!.d_postnickname)
            Toast.makeText(this, "자신이 올린 글은 dip할수 없습니다.", Toast.LENGTH_SHORT).show()


    }

}
