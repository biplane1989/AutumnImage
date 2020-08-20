package com.example.autumnimage.funtion.home

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
import java.util.concurrent.Executors

class HomeAdapter(val onClicked: OnClicked) :
    ListAdapter<ImageItemView, HomeAdapter.ViewHolder>(AsyncDifferConfig.Builder<ImageItemView>(ImageDiffCallBack())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(onClicked)
    }

    class ViewHolder(itemView: View, val adapter: HomeAdapter) : RecyclerView.ViewHolder(itemView) {

        val ivServer: ImageView = itemView.findViewById(R.id.iv_server)
        val tvLoad: TextView = itemView.findViewById(R.id.tv_load)
        val progressImage: ProgressBar = itemView.findViewById(R.id.progress_image)

        fun bind(onClicked: OnClicked) {
            val imageItem = adapter.getItem(adapterPosition)

            Glide.with(itemView.context).load(imageItem.imageItem.thumb).into(ivServer)

            if (!imageItem.imageItem.downloaded) {
                tvLoad.visibility = View.VISIBLE
            } else {
                tvLoad.visibility = View.GONE
                progressImage.visibility = View.INVISIBLE
            }

            tvLoad.setOnClickListener(View.OnClickListener {
                onClicked.onClicked(adapterPosition, imageItem)
                progressImage.visibility = View.VISIBLE

            })

//            tvLoad.setOnLongClickListener(View.OnLongClickListener {
//
//                true
//            })
        }
    }
}
