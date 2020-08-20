package com.example.autumnimage

import android.app.Application
import com.example.autumnimage.core.db.ImageDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ImageDatabase.create(this)
    }

}