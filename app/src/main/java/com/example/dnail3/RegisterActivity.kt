package com.example.dnail3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import kotlinx.android.synthetic.main.activity_3register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3register)
        addEvents()
    }

    private fun addEvents() {
        button_registerActivity_next.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
//                var checkAllEditIsVailed = true
//                if (!isEditTextValid(editText_register_name.text)){
//                    editText_register_name.error = "Họ tên chưa đúng"
//                    checkAllEditIsVailed = false
//                }
//                if (!isEditTextValid(editText_register_email.text)){
//                    editText_register_email.error = "Email chưa đúng"
//                    checkAllEditIsVailed = false
//                }
//
//                if (checkAllEditIsVailed == true){
                    val intent = Intent(this@RegisterActivity, PhoneScreenActivity::class.java)
//                    intent.putExtra("nameFormRegisterActivity", editText_register_name.text)
//                    intent.putExtra("emailFormRegisterActivity", editText_register_email.text)
                    startActivity(intent)
//                }
            }
        })

        image_registerActivity_back.setOnClickListener (object: View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }

    private fun isEditTextValid(text: Editable?): Boolean {
        return text != null && text.length >= 1
    }
}
