package com.example.animewallz2022.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animewallz2022.datamodel.CategoryData
import com.example.animewallz2022.datamodel.TrendingData
import com.example.animewallz2022.datamodel.WallData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WallpaperViewModel : ViewModel(){

    var bomList : MutableLiveData<List<WallData>> = MutableLiveData()
    val _bomlist : LiveData<List<WallData>> = bomList

    var catList : MutableLiveData<List<CategoryData>> = MutableLiveData()
    val _catList : LiveData<List<CategoryData>> = catList

    var trendingList : MutableLiveData<List<TrendingData>> = MutableLiveData()
    val _trendingList : LiveData<List<TrendingData>> = trendingList

    var downloadedList : MutableLiveData<List<String>> = MutableLiveData()
    val _downloadedList : LiveData<List<String>> = downloadedList

    private val repository = WallpaperRepository()

    fun getBOMlist() : LiveData<List<WallData>> {
        GlobalScope.launch {
            repository.getBOMList(bomList)
        }
        return _bomlist
    }

    fun getCatlist() : LiveData<List<CategoryData>>{
        GlobalScope.launch {
            repository.getCategoriesList(catList)
        }
        return _catList
    }

    fun getTrending() : LiveData<List<TrendingData>>{
        viewModelScope.launch {
            repository.getTrendingWallList(trendingList)
        }
        return _trendingList
    }

    fun getDownloaded() : LiveData<List<String>>{
        viewModelScope.launch {
            repository.getDownloadedWallList(downloadedList)

        }
        return _downloadedList
    }

}