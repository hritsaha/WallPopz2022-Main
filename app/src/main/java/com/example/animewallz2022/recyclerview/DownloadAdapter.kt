package com.example.animewallz2022.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.animewallz2022.R
import com.example.animewallz2022.onClickWall
import com.squareup.picasso.Picasso
import java.io.File

class DownloadAdapter(var mList: List<String>,private val onClickWall: onClickWall) : RecyclerView.Adapter<DownloadAdapter.ViewHolder>(){
    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){
        val listImage: ImageView=ItemView.findViewById(R.id.list_wall_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_wall,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(File(mList[position])).fit().into(holder.listImage)
        holder.itemView.setOnClickListener {
            onClickWall.onWallClicked(mList[position].toString())
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}