package com.example.dnail3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_7persional_information.*

class PersonalInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_7persional_information)

        image_personalInformation_back.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@PersonalInformationActivity, PhoneScreenActivity::class.java)
                startActivity(intent)
            }
        })
        image_personalInformation_edit.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@PersonalInformationActivity, EditProfileActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
