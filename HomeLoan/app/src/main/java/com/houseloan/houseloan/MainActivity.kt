package com.houseloan.houseloan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val lawyerFee = findViewById<TextView>(R.id.lawyer_fee_id)
        lawyerFee.text = "2.500"
    }

    fun sendMessage(view: View) {
        updatePrice()
    }

    private fun updatePrice() {
        val totalPrice = findViewById<TextView>(R.id.total_price_id)
        val total = totalPrice.text.toString().toDouble()

        if(total < 400) {
            totalPrice.text = "400"
            return
        }

        val opt = findViewById<TextView>(R.id.opt_id);
        opt.text = (total * 0.05).toString()

        val afterOpt = findViewById<TextView>(R.id.extra_opt_id)
        afterOpt.text = (total * 0.15).toString()

        val stampDuty = findViewById<TextView>(R.id.stamp_duty_id)
        if (total > 1000.0) {
            stampDuty.text = (total*0.04 - 15.400).toString()
        }
        else {
            stampDuty.text = (total*0.03 - 5.400).toString()
        }

        val lawyerFee = findViewById<TextView>(R.id.lawyer_fee_id)

        val all = findViewById<TextView>(R.id.all_id)
        all.text = (total*0.28 + lawyerFee.text.toString().toDouble()).toString()

    }
}


/*

OTP: 5%:
2 weeks after OTP: 15%:
Stamp duty: 3%:
         <=100m: 3%-5400
         >100m: 4%-15400
Extra Stamp: ~
Lawyer Fee: ~2500
Foundation Completion: 5%

All: 28%

loan: 75%
 */