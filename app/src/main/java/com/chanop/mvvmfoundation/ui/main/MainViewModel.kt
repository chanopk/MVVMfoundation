package com.chanop.mvvmfoundation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.chanop.mvvmfoundation.repositiry.PokemonRepo
import com.chanop.mvvmfoundation.repositiry.service.Resource
import com.finnomena.project.candidate.repositiry.model.Kanto
import kotlinx.coroutines.launch

class MainViewModel(private val pokemonRepo: PokemonRepo) : ViewModel() {

    private val _pokemonKantoData = MutableLiveData<Kanto?>()
    val pokemonKantoData: LiveData<Kanto?> = _pokemonKantoData
    private val _pokemonKantoDataError = MutableLiveData<String?>()
    val pokemonKantoDataError: LiveData<String?> = _pokemonKantoDataError

    fun getPokemonKanto() = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(pokemonRepo.getAllPokemonKanto()))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }

    fun loadPokemonKanto() {
        viewModelScope.launch {
            try {
                val allPokemonKanto = pokemonRepo.getAllPokemonKanto()
                _pokemonKantoData.value = allPokemonKanto
            } catch (e: Exception) {
                _pokemonKantoDataError.value = "error !!! :${e.message}"
            }
        }
    }
}
