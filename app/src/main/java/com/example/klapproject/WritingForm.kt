package com.example.klapproject

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_load.*
import kotlinx.android.synthetic.main.activity_writing_form.*
import java.time.LocalDate
import java.time.LocalDateTime
import android.Manifest
import android.widget.AdapterView
import com.google.firebase.database.FirebaseDatabase

class WritingForm : AppCompatActivity() {
    val SELECT_IMAGE = 100
    val TAG = "writing_form"
    lateinit var t_d:Document    //인자: 게시자, 현재 날짜


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_form)
        t_d = Document(MY_NICK)
        init()
    }

    fun init(){
        getUrl()
        wf_ctg_spinner1.onItemSelectedListener = SpinnerSelectedListener()
        wf_is_off.setOnCheckedChangeListener { buttonView, isChecked ->
            t_d.d_off = wf_is_off.isChecked
            update_visibie()
        }
        wf_is_on.setOnCheckedChangeListener { buttonView, isChecked ->
            t_d.d_on = wf_is_on.isChecked
            update_visibie()
        }
        regi_product.setOnClickListener {
            writing_form()
            if(t_d.is_empty()){
                Toast.makeText(this, "입력하지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show()
            }else{
                var now = LocalDateTime.now()
                var timestr = "${now.year}-${now.month}-${now.dayOfMonth}T${now.hour}:${now.minute}:${now.second}"
                t_d.d_now = timestr
                MainActivity.doc_list.add(t_d)
                //디비에 등록
                t_d.regi_firebase()

                // user에 올린 게시물 추가
                var user_upload = FirebaseDatabase.getInstance().getReference("user/$MY_ID")
                var user_upload_list = user_upload.child("upload_list").push()
                user_upload_list.setValue(t_d.d_title.toString())

                var gohomeintent = Intent(this, MainActivity::class.java)
                startActivity(gohomeintent)
            }

        }
    }

    fun update_visibie(){
        if(t_d.d_off || t_d.d_on){
            wf_detail.visibility = View.VISIBLE
        }else{
            wf_detail.visibility = View.GONE
        }
    }


    fun writing_form(){
        t_d.d_title=wf_title.text.toString()
        t_d.d_market = wf_detail.text.toString()
        t_d.d_info = wf_more.text.toString()
        t_d.d_num = wf_peoplenum.text.toString()
        t_d.d_price = wf_price.text.toString()
        t_d.d_place = wf_location.text.toString()
        t_d.d_duty = wf_fee.text.toString()
    }

    fun getUrl(){
        wf_imageButton.setOnClickListener {
            Log.v(TAG, "imageButton 누름")
            if(checkAppPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))){
                Toast.makeText(this,"권한이 승인되었습니다." , Toast.LENGTH_SHORT ).show ()
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                startActivityForResult(intent,SELECT_IMAGE)
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("반드시 이미지 데이터에 대한 권한이 허용되어야 합니다.")
                    .setTitle("권한 허용")
                    .setIcon(R.drawable.abc_ic_star_black_48dp)
                builder.setPositiveButton("OK") { _, _ ->
                    askPermission (arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), SELECT_IMAGE )
                    //권한 요청
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    fun checkAppPermission(requestPermission: Array<String>): Boolean {
        val requestResult = BooleanArray(requestPermission.size)
        for (i in requestResult.indices) {
            requestResult[i] = ContextCompat.checkSelfPermission(
                this,
                requestPermission[i]
            ) == PackageManager.PERMISSION_GRANTED
            if (!requestResult[i]) {
                return false
            }
        }
        return true
    } // checkAppPermission

    fun askPermission(requestPermission: Array<String>, REQ_PERMISSION: Int) {
        ActivityCompat.requestPermissions(this, requestPermission, REQ_PERMISSION)
    } // askPermission

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                wf_imageView.setImageURI(data!!.data)
//                t_d.uri = data!!.data
                t_d.d_url = data!!.data.toString()
            }
        }
    }

    inner class SpinnerSelectedListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Log.v(TAG, position.toString()+": "+parent?.getItemAtPosition(position).toString())
            t_d.d_category = position.toString()
        }
    }

}
