package com.example.klapproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class DocumentDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_detail)
        val i = intent
        val data = i.getSerializableExtra("mydata") as Document
//        Toast.makeText(this,"${data.d_postnickname}, ${data.d_title}",Toast.LENGTH_LONG).show()
        Toast.makeText(this,"${data.d_postnickname}",Toast.LENGTH_LONG).show()
    }
}
