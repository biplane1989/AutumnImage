package com.example.autumnimage.funtion.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autumnimage.R
import com.example.autumnimage.`object`.ImageGallery
import com.example.autumnimage.databinding.ActivityGalleryScreenBinding
import com.example.autumnimage.databinding.ActivityHomeScreenBinding
import com.example.autumnimage.funtion.home.HomeAdapter
import com.example.autumnimage.funtion.home.HomeScreenDirections
import com.example.autumnimage.funtion.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_gallery_screen.*
import kotlinx.android.synthetic.main.activity_home_screen.*

class GalleryScreen : Fragment(), OnGalleryClicked {

    lateinit var galleryViewModel: GalleryViewModel
    lateinit var galleryBinding: ActivityGalleryScreenBinding
    lateinit var adapter: GalleryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)

        galleryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_gallery_screen, container, false)
        galleryBinding.lifecycleOwner = this
        galleryBinding.galleryviewmodel = galleryViewModel
        return galleryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        galleryViewModel.getListImage().observe(this, Observer { listImage ->
            adapter.submitList(ArrayList(listImage))
        })
    }

    override fun onStart() {
        super.onStart()
        galleryViewModel.initData()
    }

    private fun init() {
        adapter = GalleryAdapter(this)
        rv_gallery.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        rv_gallery.setHasFixedSize(true)
        rv_gallery.adapter = adapter

    }

    override fun onClick(imageGallery: ImageGallery) {
        Log.d("001", "onClick: ")

        val directions = GalleryScreenDirections.actionGalleryToDetail().setId(imageGallery.id)
        NavHostFragment.findNavController(this).navigate(directions)
    }
}