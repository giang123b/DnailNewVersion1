package com.example.dnail3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_3register.*
import kotlinx.android.synthetic.main.activity_8edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_8edit_profile)
        var intent = intent
        var name = intent.getStringExtra("nameFormRegisterActivity")
        var email = intent.getStringExtra("emailFormRegisterActivity")
        var phoneNumber = intent.getStringExtra("phoneNumberFromIntroduceActivity")

        editText_editProfile_email.setText(email)
        editText_editProfile_name.setText(name)
        editText_editProfile_phone.setText(phoneNumber)

        image_editProfile_back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })
    }
}
