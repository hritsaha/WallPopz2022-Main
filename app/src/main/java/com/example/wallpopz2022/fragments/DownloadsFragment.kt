package com.example.wallpopz2022.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpopz2022.R
import com.example.wallpopz2022.ViewDownloadedWallActivity
import com.example.wallpopz2022.databinding.FragmentDownloadsBinding
import com.example.wallpopz2022.onClickWall
import com.example.wallpopz2022.recyclerview.DownloadAdapter
import com.example.wallpopz2022.viewmodel.WallpaperViewModel

@Suppress("DEPRECATION")
class DownloadsFragment : Fragment(R.layout.fragment_downloads),onClickWall {
    lateinit var binding:FragmentDownloadsBinding
    lateinit var downloadadapter: DownloadAdapter
    private lateinit var viewmodel: WallpaperViewModel
    lateinit var downloadlist : List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentDownloadsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //request storage permission
        requestPermission()


        //RV - for downloads
        downloadlist=ArrayList<String>()
        val downloadrv = binding.downloadwallRv
        downloadrv.layoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        downloadadapter= DownloadAdapter(downloadlist,this)
        downloadrv.adapter=downloadadapter

        var size=0
        //MVVM
        viewmodel=ViewModelProvider(this).get(WallpaperViewModel::class.java)
        viewmodel.getDownloaded().observe(viewLifecycleOwner, Observer {
            downloadadapter.mList=it
            downloadadapter.notifyDataSetChanged()
            size=it.size
            binding.downloadsNumberText.text= "$size Wallpapers"

        })
    }



    //Funtions related to - STORAGE PERMISSIONS
    private fun hasPermission() : Boolean{
        return ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestPermission(){
        var permission = mutableListOf<String>()

        if(!hasPermission()){
            //add permission to list
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        //ask the user for the permission
        if(permission.isNotEmpty()){
            ActivityCompat.requestPermissions(context as Activity,permission.toTypedArray(),0)
        }
    }

    override fun onWallClicked(url: String) {
        val intent = Intent(context, ViewDownloadedWallActivity::class.java)
        intent.putExtra("link",url)
//        Toast.makeText(context,url.toString(), Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

}