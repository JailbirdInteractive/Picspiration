package com.rebirth.picspiration.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rebirth.picspiration.repositories.PhotoRepo

class MainViewModel: ViewModel() {
     var photos: MutableLiveData<ArrayList<Photo>>? = MutableLiveData()

    private val photoRepo:PhotoRepo = PhotoRepo


    public fun init(){

        if(!photos?.value.isNullOrEmpty()){

            return
}
    photos= photoRepo.getPhotos()
}
}