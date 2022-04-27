package com.example.animewallz2022.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.animewallz2022.R
import com.example.animewallz2022.ViewWallActivity
import com.example.animewallz2022.databinding.FragmentTrendingBinding
import com.example.animewallz2022.datamodel.TrendingData
import com.example.animewallz2022.onClickWall
import com.example.animewallz2022.recyclerview.TrendingWallAdapter
import com.example.animewallz2022.viewmodel.WallpaperViewModel


class TrendingFragment : Fragment(R.layout.fragment_trending) ,onClickWall{

    private lateinit var viewmodel : WallpaperViewModel
    private lateinit var binding:FragmentTrendingBinding
    lateinit var trendinglist : List<TrendingData>
    lateinit var trendinglistadapter : TrendingWallAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= FragmentTrendingBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshApp()

        //RV
        trendinglist=ArrayList<TrendingData>()
        val trendinglistrv = binding.trendingwallRv
        trendinglistrv.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        trendinglistadapter=TrendingWallAdapter(trendinglist,this)
        trendinglistrv.adapter=trendinglistadapter

        var size=0

        //MVVM
        viewmodel= ViewModelProvider(this).get(WallpaperViewModel::class.java)
        viewmodel.getTrending().observe(viewLifecycleOwner, Observer {
            trendinglistadapter.mList=it
            trendinglistadapter.notifyDataSetChanged()
            size=it.size
            binding.trendingNumberText.text= "$size Wallpapers"
        })

    }

    //click to open full size
    override fun onWallClicked(url: String) {
        val intent= Intent(requireContext(), ViewWallActivity::class.java)
        intent.putExtra("link",url)
        startActivity(intent)
    }

    private fun refreshApp(){
        binding.swipeToRefresh.setOnRefreshListener {
            Toast.makeText(requireContext(),"Page Refreshed",Toast.LENGTH_SHORT).show()
            trendinglistadapter.notifyDataSetChanged()
            binding.swipeToRefresh.isRefreshing= false
        }
    }
}