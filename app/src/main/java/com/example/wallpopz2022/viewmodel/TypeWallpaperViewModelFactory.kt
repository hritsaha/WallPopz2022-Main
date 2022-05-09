package com.example.wallpopz2022.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TypeWallpaperViewModelFactory(private val walltype : String) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TypeWallpaperViewModel(walltype) as T
    }
}