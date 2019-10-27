package com.example.dnail2.Times

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dnail3.R
import kotlinx.android.synthetic.main.item_time.view.*

class TimeAdapter(var mContext: Context,var timeList: List<Time>,var text_linearTimeLocation_enterTime: TextView,
                  var day: String) : RecyclerView.Adapter<TimeAdapter.MyViewHolder>() {

    private var selectedPosition = -1
    private var hour = ""

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var time = view.time!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val time = timeList[position]
        holder.time.text = time.getTime()

        if (selectedPosition == position) {
            //            holder.itemView.setBackgroundColor(Color.parseColor("#E91E63"));
            holder.itemView.setBackgroundResource(R.drawable.shape_round_full_pink)
            holder.time.setTextColor(mContext.resources.getColor(R.color.white))
        } else {
            holder.itemView.setBackgroundResource(R.drawable.shape_round_not_full_pink)
            holder.time.setTextColor(mContext.resources.getColor(R.color.pink_theme))
        }

        holder.itemView.setOnClickListener {
            selectedPosition = position
            hour = timeList[selectedPosition].getTime().toString()
            day = text_linearTimeLocation_enterTime.text as String
            day = day.substring(
                text_linearTimeLocation_enterTime.text.length - 5,
                text_linearTimeLocation_enterTime.text.length
            )
            text_linearTimeLocation_enterTime.text = "$hour - $day"
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return timeList.size
    }
}