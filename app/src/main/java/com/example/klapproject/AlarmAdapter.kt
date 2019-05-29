package com.example.klapproject

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class AlarmAdapter (val items:MutableList<String>)
    : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    // recycleview는 viewHolder라는 데이터 저장 공간을 필요로 함

    fun moveItem(pos1:Int, pos2 :Int){
        // 아이템 이동 함수
        val item1 = items.get(pos1)
        items.removeAt(pos2)
        items.add(pos2,item1) //기존 정보를 지우고 넣어서 위치 변경
        notifyItemMoved(pos1,pos2) // 아이템이 이동했음을 알리는 메시지
    }

    fun removeItem(pos: Int){
        // 아이템 삭제 함수
        items.removeAt(pos)
        notifyItemRemoved(pos) // 아이템이 삭제되었음을 알리는 메시지
    }

    inner class ViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView){
        //또한 인자로 넘겨야하는 ViewHolder는 innerClass로 선언되어야함
        // 뷰를 만들어서 홀더객체를 만들었으니, 이쪽으로 넘어옴
        // 여기서 사용할 멤버를 구성
        var item_name: TextView

        init{ //init block을 통한 초기화
            item_name = itemView.findViewById(R.id.item_name)
            // 받아온 화면에 존재하는 객체들을 찾아서 저장
        }
    }

    // 각 함수별 오버라이딩
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //View Holder 만들기

        val v = LayoutInflater.from(parent.context).
            inflate(R.layout.noticelist_layout,parent,false)
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
        holder.item_name.text = items.get(position)
    }


}
