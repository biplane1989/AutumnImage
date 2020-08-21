package com.example.autumnimage.funtion.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autumnimage.`object`.ImageGallery
import com.example.autumnimage.core.FileDownloadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    private var images: MutableLiveData<ArrayList<ImageGallery>> = MutableLiveData()
    private var _images = ArrayList<ImageGallery>()

    init {
        images.value = ArrayList()
    }

    fun getListImage(): MutableLiveData<ArrayList<ImageGallery>> {
        return images
    }

    fun initData() {
        CoroutineScope(Dispatchers.Default).launch {
            _images.clear()
            for (item in FileDownloadManager.getAllListImage()) {
                _images.add(item)
            }
            images.postValue(_images)
        }
    }

}