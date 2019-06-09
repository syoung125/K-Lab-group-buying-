package com.example.klapproject

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.koushikdutta.ion.Ion

class CategoryAdapter (var list:MutableList<Document>)
    // 객체 생성될 때 전달되는 값 Context
    // Layout의 id 정보 (int), 정보를 가진 배열 ArrayList 을 받는 생성자
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
    {
        interface OnItemClickListener{ //내가 내 클래스에서 호출할 함수를 내가 정의해놈, 내가 호출할꺼 약속
            fun OnItemClick(holder:ViewHolder,view:View,data:Document,position:Int)
        }
        var itemClickListener : OnItemClickListener? = null

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryAdapter.ViewHolder {
            val v= LayoutInflater.from(p0.context).inflate(R.layout.category_form, p0, false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class ViewHolder(itemView: View)  //사용할 멤버 구성
            :RecyclerView.ViewHolder(itemView){
            var imageView:ImageView
            var title:TextView
            var person:TextView
            var detail:TextView
            var onoff:TextView
            var money:TextView
            init{
                imageView=itemView.findViewById(R.id.c_Img)   //레이아웃에서 textView에 해당하는 것을 찾아서 멤버로 둠
                title=itemView.findViewById(R.id.c_title)
                person=itemView.findViewById(R.id.c_person)
                detail=itemView.findViewById(R.id.c_detail)
                onoff=itemView.findViewById(R.id.c_onoff)
                money=itemView.findViewById(R.id.c_money)
                //4/17-(2)
                itemView.setOnClickListener {
                    val position = adapterPosition
                    itemClickListener?.OnItemClick(this,it,list[position],position)
                }

            }
        }


        override fun onBindViewHolder(p0: ViewHolder, p1: Int)  //뷰홀더의 포지션 정보 오고 멤버들을 초기화,데이터연결
        {
            //viewHolder가 가지고 있는 내역들을 여기서 초기화
            val storageReference = FirebaseStorage.getInstance().reference
            Ion.with(p0.imageView).load(list.get(p1).d_storageFileName)
            p0.title.text = list.get(p1).d_title
            p0.person.text = list.get(p1).d_num.toString()+" 명"
            p0.detail.text = list.get(p1).d_info
            var t_str:String =""
            var on=list.get(p1).d_on
            var off=list.get(p1).d_off
            if(on) t_str +="on"
            if(on && off) t_str += "/"
            if(off) t_str += "off"
            p0.onoff.text = t_str
            p0.money.text = list.get(p1).d_price.toString()+" 원"

        }

    }