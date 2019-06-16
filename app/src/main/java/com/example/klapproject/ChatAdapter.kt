package com.example.klapproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ChatAdapter (context: Context, val resource:Int, var list:ArrayList<Chat>)
    : ArrayAdapter<Chat>(context, resource, list)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        if(v == null){
            val vi = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(R.layout.chat_row, null)
        }
        val p = list.get(position)
        if(p.type == true){ // 나
            v!!.findViewById<LinearLayout>(R.id.yourLayout).visibility = View.GONE
            v!!.findViewById<LinearLayout>(R.id.myLayout).visibility = View.VISIBLE
            v!!.findViewById<TextView>(R.id.myContent).text = p.content
            v!!.findViewById<TextView>(R.id.myTime).text = p.time
        }
        else{ //상대방
            v!!.findViewById<LinearLayout>(R.id.myLayout).visibility = View.GONE
            v!!.findViewById<LinearLayout>(R.id.yourLayout).visibility = View.VISIBLE
           // v!!.findViewById<ImageView>(R.id.yourImg).setImageResource(p.userImg)
            v!!.findViewById<TextView>(R.id.yourId).text = p.userId
            v!!.findViewById<TextView>(R.id.yourContent).text = p.content
            v!!.findViewById<TextView>(R.id.yourTime).text = p.time
        }
        return v
    }
}