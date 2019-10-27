package com.example.dnail2.Products

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dnail3.R
import kotlinx.android.synthetic.main.item_model.view.*

class ModelAdapter(var mContext: Context, var modelList: List<Model>, var txtMoney: TextView,
                   var text_linearBookSuccessful_selectedModel: TextView,var text_linearBookSuccessful_selectedModelPrice: TextView,
                   var image_linearBookSuccessful_selectedModel: ImageView) : RecyclerView.Adapter<ModelAdapter.MyViewHolder>() {

    private var selectedPosition = -1

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         var txtNameModel = view.txtNameModel
         var txtPriceModel = view.txtPriceModel
         var thumbnail = view.thumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = modelList[position]
        holder.txtNameModel.text = model.getName()
        holder.txtPriceModel.text = model.getprice().toString() + "K"

        // loading model cover using Glide library
        Glide.with(mContext).load(model.getThumbnail()).into(holder.thumbnail)

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#fd718b"))
        } else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

        holder.itemView.setOnClickListener { v ->
            selectedPosition = position

            // Set text money in bottom bar
            val m = modelList[selectedPosition]
            txtMoney.text = m.getprice().toString() + "K"

            // Set dialog show information of model
            val dialog = Dialog(v.context)
            dialog.setContentView(R.layout.item_model)
            dialog.setTitle("Position $position")
            dialog.setCancelable(true) // dismiss when touching outside Dialog

            // set the custom dialog components - texts and image
            val name = dialog.findViewById(R.id.txtNameModel) as TextView
            val price = dialog.findViewById(R.id.txtPriceModel) as TextView
            val thumbnail = dialog.findViewById(R.id.thumbnail) as ImageView

            setDataToView(name, price, thumbnail, position)

            val window = dialog.window
            window!!.setLayout(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            dialog.show()

            // Set text in linear book successful
            text_linearBookSuccessful_selectedModel.setText(mContext.getString(R.string.text_da_chon) + " " + m.getName())
            text_linearBookSuccessful_selectedModelPrice.setText(mContext.getString(R.string.text_mau_nay_co_gia) + " " + m.getprice() + "K")
            image_linearBookSuccessful_selectedModel.setImageResource(m.getThumbnail())

            notifyDataSetChanged()
        }
    }

    private fun setDataToView(
        name: TextView,
        price: TextView,
        thumbnail: ImageView,
        position: Int
    ) {
        name.text = modelList[position].getName()
        price.text = modelList[position].getprice().toString() + "K"
        thumbnail.setImageResource(modelList[position].getThumbnail())
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}