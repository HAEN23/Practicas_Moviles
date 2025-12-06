package com.heber.pokemon.util

fun getPokemonImageUrl(url: String): String {
    val id = url.split("/").filter { it.isNotEmpty() }.last()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}

fun getPokemonIdFromUrl(url: String): Int {
    return url.split("/").filter { it.isNotEmpty() }.last().toIntOrNull() ?: 0
}

