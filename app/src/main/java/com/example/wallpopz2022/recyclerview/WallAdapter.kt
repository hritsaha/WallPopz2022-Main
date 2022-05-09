package com.example.wallpopz2022.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpopz2022.R
import com.example.wallpopz2022.datamodel.WallData
import com.example.wallpopz2022.onClickWall
import com.squareup.picasso.Picasso

class WallAdapter(var mList : List<WallData>,private val onClickWall: onClickWall) : RecyclerView.Adapter<WallAdapter.ViewHolder>(){
    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){
        val bomImage : ImageView =ItemView.findViewById(R.id.bom_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_bom,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(mList[position].imageLink).fit().into(holder.bomImage)

        holder.itemView.setOnClickListener {
            onClickWall.onWallClicked(mList[position].imageLink.toString())
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}