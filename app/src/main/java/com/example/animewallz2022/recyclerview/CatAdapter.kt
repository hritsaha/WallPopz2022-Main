package com.example.animewallz2022.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animewallz2022.R
import com.example.animewallz2022.datamodel.CategoryData
import com.example.animewallz2022.onClickWall
import com.squareup.picasso.Picasso

class CatAdapter(var mList : List<CategoryData>,private val onClickWall: onClickWall): RecyclerView.Adapter<CatAdapter.ViewHolder>(){
    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){
        val catText : TextView =ItemView.findViewById(R.id.cat_text)
        val catImage : ImageView = ItemView.findViewById(R.id.cat_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_cat,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.catText.text=mList[position].category.capitalize().toString()
        Picasso.get().load(mList[position].imageLink).fit().into(holder.catImage)

        holder.itemView.setOnClickListener {
            onClickWall.onWallClicked(mList[position].category)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}