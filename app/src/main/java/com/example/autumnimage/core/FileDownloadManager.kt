package com.example.autumnimage.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import com.example.autumnimage.`object`.ImageFile
import com.example.autumnimage.`object`.ImageItem
import com.example.autumnimage.core.db.DBFunction
import com.example.autumnimage.core.db.ImageDatabase
import com.example.autumnimage.core.db.entity.ImageEntity
import com.example.autumnimage.utils.SaveImageFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


object FileDownloadManager {

    val FOLDER_NAME = "holiday"

    suspend fun downloadImage(context: Context, imageItem: ImageItem): ImageFile? {

        var data: Bitmap
        val response = ApiHelper.getPhoto(imageItem.thumb)
        try {
            data = BitmapFactory.decodeStream(response.byteStream())
            val path = SaveImageFile.saveImageToInternalStorage(context, data, FOLDER_NAME, imageItem.url)
            return DBFunction.saveImage(imageItem.url, path)
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun isDownloaded(imageItem: ImageItem): Boolean {

        val listImageDB = DBFunction.getAllImage()
        for (image in listImageDB) {
            if (TextUtils.equals(image.url, imageItem.url)) {
                return true
            }
        }
        return false;
    }
}