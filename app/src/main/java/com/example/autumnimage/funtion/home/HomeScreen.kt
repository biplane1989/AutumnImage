package com.example.autumnimage.funtion.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autumnimage.MyApplication
import com.example.autumnimage.R
import com.example.autumnimage.core.db.DBFunction
import com.example.autumnimage.core.db.ImageDatabase
import com.example.autumnimage.databinding.ActivityHomeScreenBinding
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.coroutines.*

class HomeScreen : Fragment(), OnClicked {
    val TAG = "001"
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeBinding: ActivityHomeScreenBinding
    private lateinit var adapter: HomeAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (isNetworkConnected()) {
            Toast.makeText(MyApplication().applicationContext, "open net work", Toast.LENGTH_SHORT)
                .show()
            homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
            homeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_home_screen, container, false)
            homeBinding.lifecycleOwner = this
            homeBinding.homeviewmodel = homeViewModel

        }
        return homeBinding.root
//        CoroutineScope(Dispatchers.Default).launch {
//            for (item in DBFunction.getAllImage()) Log.d(TAG, "onCreateView: " + item.url)
//        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!isNetworkConnected()) {
            Toast.makeText(MyApplication().applicationContext, "open net work", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isNetworkConnected()) {
            init()
            tv_internet.visibility = View.GONE

            homeViewModel.getListImage().observe(viewLifecycleOwner, Observer { listImage ->
                adapter.submitList(ArrayList(listImage))
                Log.d(TAG, "getListSize: list size " + listImage.size)
            })

//            // change status loading
//            homeViewModel.loadMore().observe(viewLifecycleOwner, Observer { statusLoad ->
//                if (LoadMoreState.DONE == statusLoad.loadingState) {
//                    homeBinding.progressBar.visibility = View.INVISIBLE
//                }
//                if (LoadMoreState.LOADING == statusLoad.loadingState) {
//                    homeBinding.progressBar.visibility = View.VISIBLE
//                }
//            })

            // go to gallery screen
            homeBinding.fab.setOnClickListener(View.OnClickListener {
                val directions = HomeScreenDirections.actionHoneToGallery()
                NavHostFragment.findNavController(this).navigate(directions)
            })
        }
    }


    private fun init() {
        adapter = HomeAdapter(this)
        rv_images.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        rv_images.setHasFixedSize(true)
        rv_images.adapter = adapter

        initScrollListener()
    }

    // item clicked
    override fun onClicked(position: Int, imageItemView: ImageItemView, textView: TextView, progressBar: ProgressBar) {
        Log.d(TAG, "onClicked: " + position)
        CoroutineScope(Dispatchers.Main).launch {
//            progressBar.visibility = View.VISIBLE

            homeViewModel.saveImage(activity!!.applicationContext, imageItemView.imageItem, position)

            textView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    var status = true

    // Scorll list and loadmore data
    fun initScrollListener() {
        homeBinding.rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val gridLayoutManager: GridLayoutManager = homeBinding.rvImages.layoutManager as GridLayoutManager

                if (status) {
                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == homeViewModel.getListSize() - 1) {
                        Log.d(TAG, "onScrolled: list size: " + homeViewModel.getListSize())

                        status = false
                        CoroutineScope(Dispatchers.Main).launch {
                            progress_bar.visibility = View.VISIBLE
                            delay(2000)
                            homeViewModel.loadMore()
                            progress_bar.visibility = View.GONE
                            status = true
                        }

                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo()!!.isConnected()
    }

}

