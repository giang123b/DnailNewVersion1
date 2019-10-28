package com.example.dnail3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import kotlinx.android.synthetic.main.activity_1introduce.*

class IntroduceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1introduce)
        addEvents()
    }

    fun addEvents(){
        button_1introduceActivity_next.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (!isPhoneValid(editText_introFragment_phoneNumber.text)) {
                    editText_introFragment_phoneNumber.error =
                        getString(R.string.text_sdt_khong_dung)
                }
                else {
                    val intent = Intent(this@IntroduceActivity, OtpActivity::class.java)
                    var phone : String = editText_introFragment_phoneNumber.text.toString()
                    if (phone == null){
                        phone = "123"
                    }
//                    intent.putExtra("phoneNumberFromIntroduceActivity", phone )
                    startActivity(intent)
                }
            }
        })
    }

    private fun isPhoneValid(text: Editable?): Boolean {
        return text != null && text.length >= 0
    }
}
