package com.example.autumnimage.core.db

import com.example.autumnimage.`object`.ImageFile
import com.example.autumnimage.`object`.ImageGallery
import com.example.autumnimage.core.db.entity.ImageEntity

object DBFunction {
    suspend fun saveImage(url: String, filePath: String): ImageFile? {
        val imageEntity = ImageEntity(null, url, filePath)
        val id = ImageDatabase.getInstance().imageDAO().insert(imageEntity)
        if (id > 0) {
            return ImageFile(url, filePath)
        } else {
            return null;
        }
    }

    suspend fun getAllImage(): List<ImageEntity> {
        return ImageDatabase.getInstance().imageDAO().getAll()
    }

    suspend fun deleteImageDB(imageEntity: ImageEntity) {
        ImageDatabase.getInstance().imageDAO().delete(imageEntity)
    }

    suspend fun getImageByID(id: Int): ImageGallery {
        val imageEntity = ImageDatabase.getInstance().imageDAO().getUserById(id)
        return ImageGallery(id, imageEntity.url, imageEntity.uri)
    }
}