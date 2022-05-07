package com.example.animewallz2022.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animewallz2022.*
import com.example.animewallz2022.databinding.FragmentHomeBinding
import com.example.animewallz2022.datamodel.ColorData
import com.example.animewallz2022.datamodel.WallData
import com.example.animewallz2022.recyclerview.ColorAdapter
import com.example.animewallz2022.recyclerview.WallAdapter
import com.example.animewallz2022.util.SharedPref
import com.example.animewallz2022.viewmodel.WallpaperViewModel


class HomeFragment : Fragment(R.layout.fragment_home), onClickWall,onClickColor {
    private lateinit var viewmodel:WallpaperViewModel
    lateinit var  binding: FragmentHomeBinding
    lateinit var bomlist : List<WallData>
    lateinit var bomadapter : WallAdapter
    lateinit var coloradapter: ColorAdapter

    internal lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TOGGLE - Dark- night mode
        setTheme()


        //RV - FOR BOM
        bomlist=ArrayList<WallData>()
        val bomrv = binding.bomRv
        bomrv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        bomadapter=WallAdapter(bomlist,this)
        bomrv.adapter=bomadapter



        //MVVM - FORM BOM
        viewmodel=ViewModelProvider(this).get(WallpaperViewModel::class.java)
        viewmodel.getBOMlist().observe(viewLifecycleOwner, Observer {
            bomadapter.mList=it
            bomadapter.notifyDataSetChanged()
        })



        //COLOR VALUES    -   will perform index wise click
        val listColors : ArrayList<ColorData> = ArrayList<ColorData>()
        listColors.add(0,ColorData("green","#2AFF66"))
        listColors.add(1,ColorData("red","#FF2A43"))
        listColors.add(2,ColorData("black","#000000"))
        listColors.add(3,ColorData("purple","#7B2AFF"))
        listColors.add(4,ColorData("yellow","#EEFF41"))
        listColors.add(5,ColorData("blue","#2AA5FF"))
        listColors.add(6,ColorData("grey","#90A4AE"))
        listColors.add(7,ColorData("orange","#FF832A"))



        //RV- FOR COLOR
        val colorv = binding.colorRv
        colorv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        coloradapter=ColorAdapter(listColors,this)
        colorv.adapter=coloradapter

    }


    //handling CLICK to VIEW WALLPAPER
    override fun onWallClicked(url: String) {
        val intent = Intent(context,ViewWallActivity::class.java)
        intent.putExtra("link",url)
        startActivity(intent)
    }

    override fun onColorClicked(color: String) {
        val intent=Intent(context,ListWallActivity::class.java)
        intent.putExtra("cat",color)
        startActivity(intent)
    }


    //handling DARK-LIGHT theme
    private fun setTheme(){
        sharedPref = SharedPref(requireContext())
        if(sharedPref.loadNightModeState() == true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.darkLightMode.setImageResource(R.drawable.ic_light_mode_fig)
        }
        else{
            binding.darkLightMode.setImageResource(R.drawable.ic_dak_mode_fig)
        }
//        binding.darkLightMode.setOnCheckedChangeListener { buttonView, isChecked ->
//            if(isChecked){
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                sharedPref.setNightModeState(true)
//            }
//            else{
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                sharedPref.setNightModeState(false)
//            }
//        }
        binding.darkLightMode.setOnClickListener {
            if(sharedPref.loadNightModeState() == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref.setNightModeState(false)

            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref.setNightModeState(true)

            }
        }
    }
}