package com.example.dnail3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_2otp.*

class OtpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2otp)
        timeCountDown()
        addEvents()
    }

    private fun addEvents() {
        button_otpActivity_next.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@OtpActivity, RegisterActivity::class.java);
                startActivity(intent)
            }
        })

        image_otpActivity_back.setOnClickListener (object: View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

//        var intent = intent
//        var phoneNumber = intent.getStringExtra("phoneNumberFromIntroduceActivity")
//        phoneNumber = phoneNumber.substring(1)
//        text_otpFragment_phoneNumber.setText("+84" + phoneNumber)
    }

    private fun timeCountDown() {
        var w : CountDownTimer = object: CountDownTimer(30000, 1000) {
            override fun onTick(mil: Long) {
                textView_otpActivity_secondCountdown.setText("Gửi lại mã OTP sau: " + mil / 1000 + " giây")
            }

            override fun onFinish() {
                textView_otpActivity_secondCountdown.setText("Gửi lại mã OTP")
            }
        }.start()
    }
}
