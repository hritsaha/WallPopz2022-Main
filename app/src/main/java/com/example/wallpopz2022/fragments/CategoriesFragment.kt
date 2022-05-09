package com.example.wallpopz2022.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpopz2022.ListWallActivity
import com.example.wallpopz2022.R
import com.example.wallpopz2022.databinding.FragmentCategoriesBinding
import com.example.wallpopz2022.datamodel.CategoryData
import com.example.wallpopz2022.onClickWall
import com.example.wallpopz2022.recyclerview.CatAdapter
import com.example.wallpopz2022.viewmodel.WallpaperViewModel

class CategoriesFragment : Fragment(R.layout.fragment_categories) ,onClickWall{
    private lateinit var viewmodel:WallpaperViewModel
    private lateinit var binding: FragmentCategoriesBinding
    lateinit var catlist : List<CategoryData>
    lateinit var catadapter :CatAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= FragmentCategoriesBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RV - FOR CATEGORY LIST
        catlist=ArrayList<CategoryData>()
        val catrv = binding.categoriesRv
        catrv.layoutManager=LinearLayoutManager(context)
        catadapter= CatAdapter(catlist,this)
        catrv.adapter=catadapter


        //MVVM - FOR CATEGORY LIST
        viewmodel=ViewModelProvider(this).get(WallpaperViewModel::class.java)
        viewmodel.getCatlist().observe(viewLifecycleOwner, Observer {
            catadapter.mList=it
            catadapter.notifyDataSetChanged()
        })

    }

    override fun onWallClicked(url: String) {
        val intent= Intent(context,ListWallActivity::class.java)
        intent.putExtra("cat",url)
        startActivity(intent)
    }

}