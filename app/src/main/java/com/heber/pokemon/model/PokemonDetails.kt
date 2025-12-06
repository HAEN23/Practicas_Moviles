package com.heber.pokemon.model

data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val stats: List<Stat>
)

data class Ability(
    val ability: AbilityInfo
)

data class AbilityInfo(
    val name: String
)

data class Stat(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)

