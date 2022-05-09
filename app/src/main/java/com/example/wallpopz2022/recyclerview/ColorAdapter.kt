package com.example.wallpopz2022.recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpopz2022.R
import com.example.wallpopz2022.datamodel.ColorData
import com.example.wallpopz2022.onClickColor

class ColorAdapter(var mList : List<ColorData>,private val onClickColor: onClickColor) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){
        val colorImage : ImageView = ItemView.findViewById(R.id.color_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorAdapter.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_color,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ColorAdapter.ViewHolder, position: Int) {
        holder.colorImage.setBackgroundColor(Color.parseColor(mList[position].color.toString()))
        holder.itemView.setOnClickListener {
            onClickColor.onColorClicked(mList[position].colorName)
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }




}