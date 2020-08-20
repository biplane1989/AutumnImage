package com.example.autumnimage.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.autumnimage.core.db.dao.ImageDAO
import com.example.autumnimage.core.db.entity.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDAO(): ImageDAO

    companion object {

        @Volatile
        private var INSTANCE: ImageDatabase? = null
        fun create(context: Context) {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        fun getInstance(): ImageDatabase {
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ImageDatabase::class.java, "Images.db")
                .build()
    }

}