package com.example.autumnimage.funtion.detail

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.autumnimage.R
import com.example.autumnimage.`object`.ImageGallery
import com.example.autumnimage.databinding.ActivityDetailScreenBinding
import com.example.autumnimage.funtion.gallery.GalleryScreenDirections
import com.example.autumnimage.funtion.home.HomeScreenDirections
import com.example.autumnimage.utils.Constance
import kotlinx.android.synthetic.main.activity_detail_screen.*


class DetailScreen : Fragment() {

    private var idImage: Int = 0
    lateinit var detailViewModel: DetailViewModel
    lateinit var detailBinding: ActivityDetailScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        detailBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_detail_screen, container, false)
        detailBinding.lifecycleOwner = this
        detailBinding.detailviewmodel = detailViewModel
        return detailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        idImage = arguments?.let { DetailScreenArgs.fromBundle(it).id }!!

        detailViewModel.getImageById(idImage)

        detailViewModel.getImage().observe(this, Observer { image ->
            Glide.with(this).load(image.path).into(iv_detail)
        })

        fab_delete.setOnClickListener(View.OnClickListener {
            checkDeletedialog()
        })

    }

    private fun checkDeletedialog() {
        val alertDialogBuilder = AlertDialog.Builder(context!!)
        alertDialogBuilder.setMessage(Constance.MESSGESS_DIALOG)
        alertDialogBuilder.setPositiveButton(Constance.YES, object : DialogInterface.OnClickListener {
            override fun onClick(arg0: DialogInterface?, arg1: Int) {
                detailViewModel.deleteImage()
                val directions = DetailScreenDirections.actionDetailToGallery()
                NavHostFragment.findNavController(this@DetailScreen).navigate(directions)
            }
        })

        alertDialogBuilder.setNegativeButton(Constance.NO, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}

