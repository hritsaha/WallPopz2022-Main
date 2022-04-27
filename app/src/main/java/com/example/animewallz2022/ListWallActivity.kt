package com.example.animewallz2022

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.animewallz2022.databinding.ActivityListWallBinding
import com.example.animewallz2022.datamodel.WallData
import com.example.animewallz2022.recyclerview.ListWallAdapter
import com.example.animewallz2022.viewmodel.TypeWallpaperViewModel
import com.example.animewallz2022.viewmodel.TypeWallpaperViewModelFactory

@Suppress("DEPRECATION")
class ListWallActivity : AppCompatActivity() ,onClickWall{

    private lateinit var viewmodel: TypeWallpaperViewModel
    lateinit var binding: ActivityListWallBinding
    lateinit var walllist : List<WallData>
    lateinit var walllistadapter : ListWallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListWallBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)


        //handling back button
        binding.catBackButton.setOnClickListener {
            finish()
        }


        //getting cat name from CategoriesFragment
        val cat = intent.getStringExtra("cat")
        val catName : String = cat.toString()
        var flag : Int=0
        if(catName.equals("red") || catName.equals("green") || catName.equals("black") || catName.equals("grey")
                || catName.equals("purple")|| catName.equals("orange") || catName.equals("yellow") || catName.equals("blue")){
            flag=1
        }

        //Setting title as per Category we received
        binding.catTitleText.text=catName.capitalize()
        var size = 0


        //RV
        walllist=ArrayList<WallData>()
        val walllistrv = binding.listwallRv
        walllistrv.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        walllistadapter= ListWallAdapter(walllist,this)
        walllistrv.adapter=walllistadapter

        //mvvm
        val factory = TypeWallpaperViewModelFactory(catName)
        viewmodel=ViewModelProvider(this,factory).get(TypeWallpaperViewModel::class.java)
        if(flag == 1){
            viewmodel.getColorlist().observe(this, Observer {
                walllistadapter.mList=it
                walllistadapter.notifyDataSetChanged()
                size=it.size
                binding.catNumberText.text= "$size Wallpapers"
            })
        }
        else{
            viewmodel.gettypewalllist().observe(this, Observer {
                walllistadapter.mList=it
                walllistadapter.notifyDataSetChanged()
                size=it.size
                binding.catNumberText.text= "$size Wallpapers"
            })
        }

        binding.catNumberText.text=walllist.size.toString()+" wallpapers"



    }


    //click to open full size
    override fun onWallClicked(url: String) {
        val intent= Intent(this,ViewWallActivity::class.java)
        intent.putExtra("link",url)
        startActivity(intent)
    }
}