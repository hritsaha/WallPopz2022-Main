package com.example.animewallz2022.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.animewallz2022.R
import com.example.animewallz2022.datamodel.WallData
import com.example.animewallz2022.onClickWall
import com.squareup.picasso.Picasso

class ListWallAdapter(var mList : List<WallData>,private val onClickWall: onClickWall) : RecyclerView.Adapter<ListWallAdapter.ViewHolder>(){
    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){
        val listImage : ImageView =ItemView.findViewById(R.id.list_wall_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_wall,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(mList[position].imageLink).fit().into(holder.listImage)
        holder.listImage.setOnClickListener {
            onClickWall.onWallClicked(mList[position].imageLink.toString())
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}