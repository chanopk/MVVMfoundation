package com.chanop.mvvmfoundation.repositiry.service

import com.finnomena.project.candidate.repositiry.service.ApiInterface

class ApiHelper(private val apiInterface: ApiInterface) {
    suspend fun getAllPokemonKanto() = apiInterface.getAllPokemonKanto()

    suspend fun getSelectPokemon(keyword: String) = apiInterface.getSelectPokemon(keyword)
}
