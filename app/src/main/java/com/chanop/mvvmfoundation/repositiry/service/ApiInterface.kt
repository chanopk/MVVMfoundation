package com.finnomena.project.candidate.repositiry.service

import com.finnomena.project.candidate.repositiry.model.Kanto
import com.finnomena.project.candidate.repositiry.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/api/v2/pokedex/2/")
    suspend fun getAllPokemonKanto(): Kanto

    @GET("/api/v2/pokemon/{keyword}")
    suspend fun getSelectPokemon(@Path("keyword") keyword: String): Pokemon
}
