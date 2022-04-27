package com.example.animewallz2022.viewmodel

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.example.animewallz2022.datamodel.CategoryData
import com.example.animewallz2022.datamodel.TrendingData
import com.example.animewallz2022.datamodel.WallData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.io.File

class WallpaperRepository {
    val firestore = FirebaseFirestore.getInstance()

     suspend fun getBOMList(liveData : MutableLiveData<List<WallData>>) {
//        firestore.collection("wallpapers").get()
//            .addOnSuccessListener {documents ->
//                run {
//                    for(document in documents){
//                        val u  =documents.toObjects(WallData::class.java)
//                        liveData.postValue(u)
//                    }
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG,"Failed to Load Data")
//            }

         firestore.collection("wallpapers").whereEqualTo("bestOfMonth",true).addSnapshotListener { value, error ->
             val data=value?.toObjects(WallData::class.java)
             liveData.postValue(data!!)
         }

    }


    suspend fun getCategoriesList(liveData: MutableLiveData<List<CategoryData>>){
        firestore.collection("categories").orderBy("id",Query.Direction.ASCENDING).addSnapshotListener { value, error ->
            val data=value?.toObjects(CategoryData::class.java)
            liveData.postValue(data!!)
        }
    }


    suspend fun getTrendingWallList(liveData: MutableLiveData<List<TrendingData>>){
        firestore.collection("trending").orderBy("id",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            val data = value?.toObjects(TrendingData::class.java)
            liveData.postValue(data!!)
        }
    }

    suspend fun getTypeWallList(type : String,liveData: MutableLiveData<List<WallData>>){
        firestore.collection("wallpapers").whereEqualTo("category",type).addSnapshotListener { value, _ ->
            val data = value?.toObjects(WallData::class.java)
            liveData.postValue(data!!)
        }
    }

    suspend fun getColorList(colorname : String,liveData: MutableLiveData<List<WallData>>){
        firestore.collection("wallpapers").whereEqualTo("colorTag",colorname).addSnapshotListener { value, _ ->
            val data = value?.toObjects(WallData::class.java)
            liveData.postValue(data!!)
        }
    }

    suspend fun getDownloadedWallList(liveData: MutableLiveData<List<String>>){
        val allFiles : Array<File>
        val wallList = ArrayList<String>()

        val targetPath = Environment.getExternalStorageDirectory().absolutePath+"/Pictures/WallPop-2022"
        val targetFile = File(targetPath)
        if(targetFile.exists()){
            allFiles=targetFile.listFiles()!!
            if(allFiles.isNotEmpty()){
                for(data in allFiles){
                    wallList.add(data.absolutePath)
                }
            }
        }

        liveData.postValue(wallList!!)

    }
}