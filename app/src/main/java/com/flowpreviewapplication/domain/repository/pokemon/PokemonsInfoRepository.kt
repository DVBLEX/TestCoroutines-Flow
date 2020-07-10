package com.flowpreviewapplication.domain.repository.pokemon

import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokemonsInfoRepository {

    fun getPokemonsInfo(): Flow<List<Pokemon>>

    fun getAvailablePokemonSpecies(): Flow<List<PokemonSpecies>>

    suspend fun updatePokemon(item: Pokemon)

    suspend fun addPokemon(item: Pokemon)

    suspend fun removeAllPokemons()

}