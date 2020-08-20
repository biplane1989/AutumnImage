package com.example.autumnimage.core.db

import com.example.autumnimage.`object`.ImageFile
import com.example.autumnimage.core.db.entity.ImageEntity

object DBFunction {
    suspend fun saveImage(url: String, filePath: String): ImageFile? {
        val imageEntity = ImageEntity(null, url, filePath)
        val id = ImageDatabase.getInstance().imageDAO().insert(imageEntity)
//        if(id > 0){
        return ImageFile(url, filePath)
//        }else{
//            return null;
//        }

    }

    suspend fun getAllImage(): List<ImageEntity> {
        return ImageDatabase.getInstance().imageDAO().getAll()
    }
}