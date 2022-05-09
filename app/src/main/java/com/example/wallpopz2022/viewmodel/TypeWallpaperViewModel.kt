package com.example.wallpopz2022.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpopz2022.datamodel.WallData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TypeWallpaperViewModel(private val walltype:String) : ViewModel() {

    var typewallList : MutableLiveData<List<WallData>> = MutableLiveData()
    var _typewallList : LiveData<List<WallData>> = typewallList


    var colorList : MutableLiveData<List<WallData>> = MutableLiveData()
    val _colorlist : LiveData<List<WallData>> = colorList

    private val repository = WallpaperRepository()

    fun gettypewalllist() : LiveData<List<WallData>>{
        GlobalScope.launch {
            repository.getTypeWallList(walltype,typewallList)
        }

        return _typewallList
    }

    fun getColorlist() : LiveData<List<WallData>>{
        GlobalScope.launch {
            repository.getColorList(walltype,colorList)
        }

        return _colorlist
    }


}