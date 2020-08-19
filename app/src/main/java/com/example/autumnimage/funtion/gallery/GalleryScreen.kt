package com.example.autumnimage.funtion.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.autumnimage.R
import com.example.autumnimage.databinding.ActivityGalleryScreenBinding
import com.example.autumnimage.databinding.ActivityHomeScreenBinding
import com.example.autumnimage.funtion.home.HomeViewModel

class GalleryScreen : Fragment() {

    lateinit var galleryViewModel: GalleryViewModel
    lateinit var galleryBinding: ActivityGalleryScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)

        galleryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_gallery_screen, container, false)
        galleryBinding.lifecycleOwner = this
        galleryBinding.galleryviewmodel = galleryViewModel
        return galleryBinding.root
    }
}