package com.example.autumnimage.funtion.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.autumnimage.R
import com.example.autumnimage.`object`.ImageGallery
import com.example.autumnimage.funtion.home.HomeAdapter
import com.example.autumnimage.funtion.home.ImageDiffCallBack
import com.example.autumnimage.funtion.home.ImageItemView
import com.example.autumnimage.funtion.home.OnClicked
import java.util.concurrent.Executors


class GalleryAdapter(val onClicked: OnGalleryClicked) :
    ListAdapter<ImageGallery, GalleryAdapter.ViewHolder>(AsyncDifferConfig.Builder<ImageGallery>(GalleryDiffCallBack())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onClicked)
    }

    class ViewHolder(itemView: View, val adapter: GalleryAdapter) :
        RecyclerView.ViewHolder(itemView) {

        val ivGallery: ImageView = itemView.findViewById(R.id.iv_gallery)

        fun bind(onClicked: OnGalleryClicked) {
            val imageItem = adapter.getItem(adapterPosition)

            Glide.with(itemView.context).load(imageItem.path).into(ivGallery)

            itemView.setOnClickListener(View.OnClickListener {
                onClicked.onClick(imageItem)
            })
        }
    }


}