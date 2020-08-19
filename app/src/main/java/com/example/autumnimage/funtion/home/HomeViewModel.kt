package com.example.autumnimage.funtion.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autumnimage.core.ApiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private var page = 0;
    private var images: MutableLiveData<ArrayList<ImageItem>> = MutableLiveData()
    private var _images = ArrayList<ImageItem>()

    init {
        images.value = ArrayList()
        initData();
    }

    fun getListImage(): MutableLiveData<ArrayList<ImageItem>> {
        return images
    }

    fun initData() {
        CoroutineScope(Dispatchers.Main).launch {
            for (item in ApiHelper.getListPhoto(page++)) {
                _images.add(ImageItem(item, true))
            }
            images.postValue(_images)
            images
        }
    }

    fun getListSize(): Int {
        return images.value!!.size
    }

}