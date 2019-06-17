package com.example.klapproject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_document_detail.*

class RecordAdapter (val items:MutableList<RecordData>)
    : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    // recycleview는 viewHolder라는 데이터 저장 공간을 필요로 함

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        //또한 인자로 넘겨야하는 ViewHolder는 innerClass로 선언되어야함
        // 뷰를 만들어서 홀더객체를 만들었으니, 이쪽으로 넘어옴
        // 여기서 사용할 멤버를 구성
        var r_title: TextView
        var r_cate:TextView
        var r_img: ImageView
        init{ //init block을 통한 초기화
            r_title = itemView.findViewById(R.id.r_title)
            r_cate = itemView.findViewById(R.id.r_cate)
            r_img = itemView.findViewById(R.id.iv_recordimg)
            // 받아온 화면에 존재하는 객체들을 찾아서 저장
        }
    }

    // 각 함수별 오버라이딩
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //View Holder 만들기

        val v = LayoutInflater.from(parent.context).
            inflate(R.layout.record_form,parent,false)
        // 레이아웃 생성 (실체화)

        return ViewHolder(v)
        // 만든 레이아웃 v객체로 viewHolder를 만들어 반환
        // ViewHolder의 생성자로 갔다가, BindViewHolder 로 넘어감
    }

    override fun getItemCount(): Int {
        // 배열에 데이터가 몇개 저장되어있는지 반환하는 함수
        return items.size
        // 저장된 배열의 크기정보를 넘겨줌
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.r_title.text = items.get(position).r_date
        holder.r_cate.text =items.get(position).r_title
        Ion.with(holder.r_img).load(items.get(position).r_img)
    }
}