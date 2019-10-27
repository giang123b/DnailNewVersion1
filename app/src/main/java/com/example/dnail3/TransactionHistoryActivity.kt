package com.example.dnail3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_8edit_profile.*
import kotlinx.android.synthetic.main.activity_9transaction_history.*

class TransactionHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_9transaction_history)

        linear_transactionHistory_1.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@TransactionHistoryActivity, TransactionDetailsActivity::class.java)
                startActivity(intent)
            }
        })
        linear_transactionHistory_2.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@TransactionHistoryActivity, TransactionDetailsActivity::class.java)
                startActivity(intent)

            }
        })
        linear_transactionHistory_3.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@TransactionHistoryActivity, TransactionDetailsActivity::class.java)
                startActivity(intent)
            }
        })
        linear_transactionHistory_4.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@TransactionHistoryActivity, TransactionDetailsActivity::class.java)
                startActivity(intent)
            }
        })

        image_transactionHistory_back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
//                val intent = Intent(this@TransactionHistoryActivity, PhoneScreenActivity::class.java)
//                startActivity(intent)
                finish()
            }
        })
    }
}
