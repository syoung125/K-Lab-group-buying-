package com.example.klapproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("chat").child(p.room_id).child("chat_list").orderByKey().limitToLast(1)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(i in p0.children){
                    val message = i.child("content").value.toString()
                    v!!.findViewById<TextView>(R.id.content).text = message
                }
            }
        })
//        val myRef2 = database.getReference("chat").child(p.room_id).child("post_id")
//        myRef2.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                val postid = p0.value.toString()
//
//                v!!.findViewById<ImageView>(R.id.itemImg).setImageResource(p.itemImg)
//            }
//        })
        return v
    }
}