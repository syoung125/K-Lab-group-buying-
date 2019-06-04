package com.example.klapproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class ChatRoomAdapter(context: Context, val resource:Int, var list:ArrayList<ChatRoom>)
    : ArrayAdapter<ChatRoom>(context, resource, list)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v:View? = convertView
        if(v == null){
            val vi = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(R.layout.room_row, null)
        }
        val p = list.get(position)
        var string = ""
        for(i in 0..p.memberList.size-1){
            if(i == p.memberList.size-1)
                string += p.memberList[i]
            else
                string += p.memberList[i] + ", "
        }
        v!!.findViewById<TextView>(R.id.id).text = string
        v!!.findViewById<TextView>(R.id.content).text = p.room_id
//        v!!.findViewById<ImageView>(R.id.userImg).setImageResource(p.userImg)
//        v!!.findViewById<ImageView>(R.id.itemImg).setImageResource(p.itemImg)
        return v
    }
}