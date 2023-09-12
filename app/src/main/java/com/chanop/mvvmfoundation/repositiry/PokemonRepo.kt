package com.chanop.mvvmfoundation.repositiry

import com.chanop.mvvmfoundation.repositiry.service.ApiHelper

class PokemonRepo(private val apiHelper: ApiHelper) {
    suspend fun getAllPokemonKanto() = apiHelper.getAllPokemonKanto()

    suspend fun getSelectPokemon(keyword: String) = apiHelper.getSelectPokemon(keyword)
}
