package com.chanop.mvvmfoundation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanop.mvvmfoundation.repositiry.PokemonRepo
import com.finnomena.project.candidate.repositiry.model.Pokemon
import kotlinx.coroutines.launch

class DetailViewModel(private val pokemonRepo: PokemonRepo) : ViewModel() {

    private val _pokemonData = MutableLiveData<Pokemon?>()
    val pokemonData: LiveData<Pokemon?> = _pokemonData
    private val _pokemonDataError = MutableLiveData<String?>()
    val pokemonDataError: LiveData<String?> = _pokemonDataError

    fun loadPokemon(entryNumber: String) {
        viewModelScope.launch {
            try {
                val allPokemonKanto = pokemonRepo.getSelectPokemon(entryNumber)
                _pokemonData.value = allPokemonKanto
            } catch (e: Exception) {
                _pokemonDataError.value = "error !!! :${e.message}"
            }
        }
    }
}
