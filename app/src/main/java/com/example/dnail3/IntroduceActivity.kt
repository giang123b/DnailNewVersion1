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
                    //oast.makeText(getActivity(), getString(R.string.text_sdt_khong_dung), Toast.LENGTH_SHORT).show();
                }
                else {
                    val intent = Intent(this@IntroduceActivity, OtpActivity::class.java)
//                    val intent1 = Intent(this@IntroduceActivity, EditProfileActivity::class.java)
                    intent.putExtra("phoneNumberFromIntroduceActivity", editText_introFragment_phoneNumber.text.toString())
//                    intent1.putExtra("phoneNumberFromIntroduceActivity", editText_introFragment_phoneNumber.text.toString())
//                    startActivity(intent1)
                    startActivity(intent)
                }
            }
        })
    }

    private fun isPhoneValid(text: Editable?): Boolean {
        return text != null && text.length >= 1
    }
}
