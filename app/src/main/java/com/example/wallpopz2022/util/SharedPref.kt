package com.example.wallpopz2022.util

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context : Context) {
    internal lateinit var mySharedPref : SharedPreferences
    init {
        mySharedPref = context.getSharedPreferences("filename",Context.MODE_PRIVATE)

    }

    fun setNightModeState(state : Boolean){
        val editor : SharedPreferences.Editor= mySharedPref.edit()
        editor.putBoolean("Night Mode",state!!)
        editor.commit()
    }

    fun loadNightModeState() : Boolean{
        return mySharedPref.getBoolean("Night Mode",false)
    }

}