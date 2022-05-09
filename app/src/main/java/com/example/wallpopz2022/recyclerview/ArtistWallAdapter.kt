package com.example.wallpopz2022.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpops2022.datamodel.ArtistWallData
import com.example.wallpopz2022.R
import com.example.wallpopz2022.onArtistClick
import com.squareup.picasso.Picasso

class ArtistWallAdapter(var mList : List<ArtistWallData>, private val onClickWall: onArtistClick) : RecyclerView.Adapter<ArtistWallAdapter.ViewHolder>(){
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
            onClickWall.onClickedArtist(mList[position].imageLink.toString(),mList[position].artistName.toString(),mList[position].artistAvatar.toString())
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}