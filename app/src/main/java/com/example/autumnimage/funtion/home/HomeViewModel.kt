package com.example.autumnimage.funtion.home

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autumnimage.`object`.ImageItem
import com.example.autumnimage.core.ApiHelper
import com.example.autumnimage.core.FileDownloadManager
import com.example.autumnimage.core.db.entity.ImageEntity
import com.google.android.material.dialog.MaterialDialogs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val TAG = "001"
    private var page = 0;
    private var images: MutableLiveData<ArrayList<ImageItemView>> = MutableLiveData()
    private var _images = ArrayList<ImageItemView>()
    private val loadMoreInfo = MutableLiveData<LoadMoreInfo>()
    private val _LoadMoreInfo = LoadMoreInfo(LoadMoreState.DONE)

    init {
        images.value = ArrayList()
        initData()
    }

    fun getListImage(): MutableLiveData<ArrayList<ImageItemView>> {
        return images
    }

    fun getListSize(): Int {
        return images.value!!.size
    }

    fun initData() {
        CoroutineScope(Dispatchers.Default).launch {
            for (item in ApiHelper.getListPhoto(++page)) {
                _images.add(ImageItemView(item))
            }
            images.postValue(_images)
        }
    }

    fun synchronizedData() {
        CoroutineScope(Dispatchers.Main).launch {
            val newListImage = ArrayList<ImageItemView>()
            for (image in _images) {
                newListImage.add(image)
            }
            var position = 0;
            for (image in newListImage) {
                if (!FileDownloadManager.isDownloaded(ImageItem(image.imageItem.url, image.imageItem.thumb, image.imageItem.raw, image.imageItem.downloaded))) {
                    val newImage = image.copy()
                    newImage.imageItem.downloaded = false
//                    newImage.status = false
                    newListImage.set(position, newImage)
                }
                position++
            }
            images.postValue(newListImage)
        }
    }

    //set visiblity loading
    fun setVisiblityLoading(position: Int) {
        val newImageList = ArrayList<ImageItemView>()

        _images.forEach {
            newImageList.add(it)
        }
        val image = newImageList.get(position).copy()
        image.imageItem.downloaded = true
        newImageList.set(position, image)
        _images = newImageList
        images.postValue(_images)
    }

//    // load more data and set status progressbar loading
//    suspend fun loadMore(): LiveData<LoadMoreInfo> {
////        if (_LoadMoreInfo.loadingState == LoadMoreState.LOADING) {
////            return loadMoreInfo
////        }
////        _LoadMoreInfo.loadingState = LoadMoreState.LOADING
////        loadMoreInfo.postValue(_LoadMoreInfo)
////        CoroutineScope(Dispatchers.Main).launch {
////            delay(500)
////            _LoadMoreInfo.loadingState = LoadMoreState.DONE
////            loadMoreInfo.postValue(_LoadMoreInfo)
//
//            for (item in ApiHelper.getListPhoto(++page)) {
//                _images.add(ImageItemView(item))
//            }
//            images.postValue(_images)
////        }
//        return loadMoreInfo
//    }

    suspend fun loadMore() {
//        if (_LoadMoreInfo.loadingState == LoadMoreState.LOADING) {
//            return loadMoreInfo
//        }
//        _LoadMoreInfo.loadingState = LoadMoreState.LOADING
//        loadMoreInfo.postValue(_LoadMoreInfo)
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(500)
//            _LoadMoreInfo.loadingState = LoadMoreState.DONE
//            loadMoreInfo.postValue(_LoadMoreInfo)

        for (item in ApiHelper.getListPhoto(++page)) {
            _images.add(ImageItemView(item))
        }
        images.postValue(_images)
//        }

    }


    suspend fun saveImage(context: Context, imageItem: ImageItem, position: Int) {

//        CoroutineScope(Dispatchers.Main).launch {
            val imageFile = FileDownloadManager.downloadImage(context, imageItem)
            if (imageFile != null) {
//                setVisiblityLoading(position)
            } else {
                Log.d("001", "saveImage: uri nulllllllllllllllll")
            }
//        }
    }
}