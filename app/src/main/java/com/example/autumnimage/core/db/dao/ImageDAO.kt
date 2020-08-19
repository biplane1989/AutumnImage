package com.example.autumnimage.core.db.dao

import androidx.room.*
import com.example.autumnimage.core.db.entity.ImageFile

@Dao
interface ImageDAO {
    @Query("SELECT * FROM images WHERE id = :id")
    fun getUserById(id: Int): ImageFile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageFile)

    @Query("SELECT * FROM images")
    fun getAll(): List<ImageFile>

    @Delete
    fun delete(image: ImageFile)

    @Query("DELETE FROM images")
    fun deleteAllImage()

    @Update
    fun update(image: ImageFile)
}