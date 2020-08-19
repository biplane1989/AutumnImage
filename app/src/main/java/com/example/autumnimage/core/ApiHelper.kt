package com.example.autumnimage.core

import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import kotlinx.coroutines.*

object ApiHelper {
    suspend fun getListPhoto(page: Int): List<UnsplashPhoto> {
        return ApiClient.getClient.getPhotos(page)
    }
}