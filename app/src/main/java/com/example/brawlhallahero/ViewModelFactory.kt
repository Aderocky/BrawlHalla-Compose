package com.example.brawlhallahero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.brawlhallahero.data.BrawlRepository
import com.example.brawlhallahero.ui.screen.detail.DetailViewModel
import com.example.brawlhallahero.ui.screen.favorite.FavViewModel
import com.example.brawlhallahero.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BrawlRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}