package com.example.autumnimage.`object`

import com.example.autumnimage.core.db.entity.ImageEntity

enum class DownloadStatus{
    LOADING,
    DONE,
    FAIL
}
data class DownloadInfo(var imageFile: ImageEntity, var status: DownloadStatus)