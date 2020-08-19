package com.example.autumnimage.funtion.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.autumnimage.R
import com.example.autumnimage.databinding.ActivityHomeScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreen : Fragment() {
    val TAG = "001"
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeBinding: ActivityHomeScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        homeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_home_screen, container, false)
        homeBinding.lifecycleOwner = this
        homeBinding.homeviewmodel = homeViewModel
        return homeBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.getListImage().observe(viewLifecycleOwner, Observer {
                for (item in it) {
                    Log.d(TAG, "onViewCreated: " + item.unsplashPhoto.id)
                }
            })
        }


        homeBinding.fab.setOnClickListener(View.OnClickListener {
            val directions = HomeScreenDirections.actionHoneToGallery()
            NavHostFragment.findNavController(this).navigate(directions)
        })

    }


}