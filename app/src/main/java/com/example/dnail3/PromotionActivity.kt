package com.example.dnail3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_11promotion.*
import kotlinx.android.synthetic.main.activity_9transaction_history.*

class PromotionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_11promotion)

        linear_promotion_1.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@PromotionActivity, PromotionDetailsActivity::class.java)
                startActivity(intent)
            }
        })
        linear_promotion_2.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@PromotionActivity, PromotionDetailsActivity::class.java)
                startActivity(intent)

            }
        })
        linear_promotion_3.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@PromotionActivity, PromotionDetailsActivity::class.java)
                startActivity(intent)
            }
        })

        image_promotion_back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
//                val intent = Intent(this@PromotionActivity, PhoneScreenActivity::class.java)
//                startActivity(intent)
                finish()
            }
        })
    }
}
