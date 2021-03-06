package com.example.autumnimage.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object SaveImageFile {

    suspend fun saveImageToInternalStorage(context: Context, bitmapImage: Bitmap, filename: String, name: String): String =
        withContext(Dispatchers.Default) {
            val cw = ContextWrapper(context)
            val directory = cw.getDir(filename, Context.MODE_PRIVATE)
            // Create imageDir
            val mypath = File(directory, "$name.jpg")
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            mypath.absolutePath
        }

    suspend fun deleteImageFile(uri: String) = withContext(Dispatchers.Default) {
        val fdelete = File(uri)
        if (fdelete.exists()) {
            fdelete.delete()
        }
    }

}