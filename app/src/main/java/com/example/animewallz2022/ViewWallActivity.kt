package com.example.animewallz2022

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animewallz2022.databinding.ActivityViewWallBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.util.*

@Suppress("DEPRECATION")
class ViewWallActivity : AppCompatActivity() {
    lateinit var binding : ActivityViewWallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewWallBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)

        //getting url from MAIN ACTIVITY(HomeFragment)
        val url = intent.getStringExtra("link")
        var urlWall = URL(url)


        //LOADING WALLPAPER TO IMAGE VIEW
        Picasso.get().load(url).fit().into(binding.myWallView)


        //handling BACK button
        binding.viewBackButton.setOnClickListener {
            finish()
        }


        //handling DOWNLOAD button
        binding.viewDownloadButton.setOnClickListener {

            val name = "WallPop_2022_${url}+.jpg"
//            val targetPath = Environment.getExternalStorageDirectory().absolutePath+"/Pictures/WallPop-2022/$name"
//            val targetFile = File(targetPath)

            val file = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val targetFile = File(file.toString() + name)

            if(targetFile.exists()){
                Toast.makeText(this,"Already Exists",Toast.LENGTH_SHORT).show()
            }
            else{
                val result: Deferred<Bitmap?> = GlobalScope.async {
                    urlWall.toBitmap()
                }

                GlobalScope.launch(Dispatchers.Main){
                    saveWall(result.await(),url.toString())
                }
            }

        }


        //handling SET-WALLPAPER button
        binding.viewSetWallpaperButton.setOnClickListener {

            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlWall.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main){
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(result.await())

            }
            val my = Toast(this)
            my.apply {
                val layout : View = LinearLayout.inflate(applicationContext,R.layout.custom_toast_set,null)
                duration=Toast.LENGTH_SHORT
                setGravity(Gravity.TOP,0,0)
                view=layout
            }.show()
        }



        //making SHARE BUTTON Work
        binding.viewShareButton.setOnClickListener {
            onShare(url.toString())
        }
    }


    //Function to Save Wallpaper into file
    private fun saveWall(image: Bitmap?,url: String) {
//        val random1 = Random.nextInt(520985)
//        val random2 = Random.nextInt(520985)

//        val name = "WallPop_${random1+random2}"
        val name = "WallPop_2022_${url}"
        val check=File(Environment.DIRECTORY_PICTURES+"/"+"WallPop-2022")
        val path=Environment.DIRECTORY_PICTURES+"/"+"WallPop-2022"+File.separator+"$name.jpg"

        if(File(path).exists()){
            Toast.makeText(this,"Already Downloaded",Toast.LENGTH_LONG).show()
        }
        else{
            var data: OutputStream
            run {
                try {
                    val resolver = contentResolver
                    val contentValues = ContentValues()
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    contentValues.put(
                            MediaStore.MediaColumns.RELATIVE_PATH,
                            Environment.DIRECTORY_PICTURES + File.separator + "WallPop-2022"
                    )
                    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
                    image?.compress(Bitmap.CompressFormat.JPEG,100,data)
                    Objects.requireNonNull<OutputStream>(data)
//                Toast.makeText(this,"Download Successful",Toast.LENGTH_SHORT).show()
                    val my = Toast(this)
                    my.apply {
                        val layout : View = LinearLayout.inflate(applicationContext,R.layout.custom_toast,null)
                        duration=Toast.LENGTH_SHORT
                        setGravity(Gravity.TOP,0,0)
                        view=layout
                    }.show()

                } catch (e: Exception) {
                    Toast.makeText(this, "Failed to Download", Toast.LENGTH_SHORT).show()
                }

            }


//            val dir= Environment.getExternalStorageDirectory().toString()
//            val myDir=File(dir+"/WallPop-2022")
//            myDir.mkdirs()
//            val fname = "$name.jpg"
//            val targetFile= File(myDir,fname)
//
//            if(targetFile.exists()){
//                Toast.makeText(this, "Already theree", Toast.LENGTH_SHORT).show()
//            }
//            try{
//                openFileOutput("$fname", MODE_PRIVATE).use {
//                    if(image!!.compress(Bitmap.CompressFormat.JPEG,100,it)){
//                        throw IOException("Failed to download")
//                    }
//                }
//            }catch (e : Exception){
//                e.printStackTrace()
//            }



        }


    }

    //convert to BITMAP
    fun URL.toBitmap() :  Bitmap? {
        return try{
            BitmapFactory.decodeStream(openStream())
        }
        catch (e : IOException){
            null
        }
    }


    //share wallpaper
    fun onShare(url:String){
        val bitmapDrawable = binding.myWallView.drawable as BitmapDrawable
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