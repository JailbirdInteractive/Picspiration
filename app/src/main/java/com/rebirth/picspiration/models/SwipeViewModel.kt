package com.rebirth.picspiration.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rebirth.picspiration.repositories.Repo

class SwipeViewModel(application: Application, val myExtraParam:String):AndroidViewModel(application) {
    var photos: MutableLiveData<ArrayList<Photo>>? = MutableLiveData()

    private val repo:Repo = Repo(myExtraParam)


    public fun init(){

        if(!photos?.value.isNullOrEmpty()){

            return
        }
        photos= repo.getPhotos()
    }
    public fun nextPage(){

        photos=repo.getMorePhotos()
    }
}