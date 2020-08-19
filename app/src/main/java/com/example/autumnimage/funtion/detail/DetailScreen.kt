package com.example.autumnimage.funtion.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.autumnimage.R
import com.example.autumnimage.databinding.ActivityDetailScreenBinding
import com.example.autumnimage.databinding.ActivityGalleryScreenBinding
import com.example.autumnimage.funtion.gallery.GalleryViewModel

class DetailScreen : Fragment() {

    class GalleryScreen : Fragment() {

        lateinit var detailViewModel: DetailViewModel
        lateinit var detailBinding: ActivityDetailScreenBinding

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

            detailBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_gallery_screen, container, false)
            detailBinding.lifecycleOwner = this
            detailBinding.detailviewmodel = detailViewModel
            return detailBinding.root
        }
    }

}