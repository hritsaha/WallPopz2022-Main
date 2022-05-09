package com.example.wallpopz2022

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.example.wallpopz2022.databinding.ActivityViewDownloadedWallBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

@Suppress("DEPRECATION")
class ViewDownloadedWallActivity : AppCompatActivity() {

    lateinit var  binding : ActivityViewDownloadedWallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewDownloadedWallBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)

        //getting url from previous activity
        val url = intent.getStringExtra("link")


        //LOADING WALLPAPER TO IMAGE VIEW
        Picasso.get().load(File(url)).fit().into(binding.myWallViewDownloaded)




        //handling BACK button
        binding.viewDownloadedBackButton.setOnClickListener {
            finish()
        }




        //handling SET-WALLPAPER button
        binding.viewDownloadedSetWallpaper.setOnClickListener {

            GlobalScope.launch(Dispatchers.Main){
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(binding.myWallViewDownloaded.drawToBitmap())

            }
            val my = Toast(this)
            my.apply {
                val layout : View = LinearLayout.inflate(applicationContext,R.layout.custom_toast_set,null)
                duration= Toast.LENGTH_SHORT
                setGravity(Gravity.TOP,0,0)
                view=layout
            }.show()
        }




        //handling DELETEbutton
        binding.viewDeleteDownloadedButton.setOnClickListener {
            File(url).delete()
            val my = Toast(this)
            my.apply {
                val layout : View = LinearLayout.inflate(applicationContext,R.layout.custom_toast_delete,null)
                duration= Toast.LENGTH_SHORT
                setGravity(Gravity.TOP,0,0)
                view=layout
            }.show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }



        //handling SHARE button
        binding.viewShareDownloadedButton.setOnClickListener {
            onShare(url.toString())
        }

    }

    //share wallpaper
    fun onShare(url:String){
        val bitmapDrawable = binding.myWallViewDownloaded.drawable as BitmapDrawable
        val bitmap=bitmapDrawable.bitmap
        val bitmapPath = MediaStore.Images.Media.insertImage(contentResolver,bitmap,"Wall Pop Wallpaper",null)
        val bitmapUri = Uri.parse(bitmapPath)
        val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.type="image/"
        intentShare.putExtra(Intent.EXTRA_STREAM,bitmapUri)
        val chooser  = Intent.createChooser(intentShare,"Download this Wallpaper")
        startActivity(chooser)
    }


    
}