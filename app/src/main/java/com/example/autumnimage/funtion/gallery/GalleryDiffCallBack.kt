package com.example.autumnimage.funtion.gallery

import androidx.recyclerview.widget.DiffUtil
import com.example.autumnimage.`object`.ImageGallery
import com.example.autumnimage.funtion.home.ImageItemView


class GalleryDiffCallBack : DiffUtil.ItemCallback<ImageGallery>() {

    override fun areItemsTheSame(oldItem: ImageGallery, newItem: ImageGallery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageGallery, newItem: ImageGallery): Boolean {
        return oldItem.equals(newItem)
    }

}