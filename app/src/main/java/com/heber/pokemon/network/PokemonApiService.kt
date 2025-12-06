package com.heber.pokemon.network

import com.heber.pokemon.model.PokemonResponse
import com.heber.pokemon.model.PokemonDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetails
}
