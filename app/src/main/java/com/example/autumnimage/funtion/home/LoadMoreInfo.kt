package com.example.autumnimage.funtion.home
enum class LoadMoreState{
    LOADING,
    DONE
}
data class LoadMoreInfo(var loadingState:LoadMoreState)