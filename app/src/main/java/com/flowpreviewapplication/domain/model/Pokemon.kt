package com.flowpreviewapplication.domain.model

data class Pokemon(
    val id: Long? = null,
    val name: String,
    val level: Int,
    val species: PokemonSpecies?
)