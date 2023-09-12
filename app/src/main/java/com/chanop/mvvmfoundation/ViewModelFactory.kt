package com.chanop.mvvmfoundation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chanop.mvvmfoundation.repositiry.PokemonRepo
import com.chanop.mvvmfoundation.repositiry.service.ApiHelper
import com.chanop.mvvmfoundation.ui.detail.DetailViewModel
import com.chanop.mvvmfoundation.ui.main.MainViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(PokemonRepo(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(PokemonRepo(apiHelper)) as T
        }
        throw IllegalArgumentException("ViewModel Class not found")
    }
}
