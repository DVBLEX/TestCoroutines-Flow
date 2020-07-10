package com.flowpreviewapplication.data.datasource.pokemon.local

import com.flowpreviewapplication.data.datasource.pokemon.local.model.PokemonDb
import com.flowpreviewapplication.data.datasource.pokemon.local.model.toDomainModel
import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.model.PokemonSpecies
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class PokemonsLocalDataSource(
    private val pokemonsDao: PokemonsDao,
    private val pokemonSpeciesDao: PokemonSpeciesDao
) {

    fun getPokemonsInfo(): Flow<List<Pokemon>> {
        return pokemonsDao.getPokemonsInfo()
            .map { it.toDomainModel() }
            .onStart { delay(2000) }
    }

    suspend fun addPokemon(item: Pokemon) {
        pokemonsDao.insert(PokemonDb.fromDomainModel(item))
    }

    suspend fun addPokemons(items: List<Pokemon>) {
        pokemonsDao.insert(items.map { PokemonDb.fromDomainModel(it) })
    }

    suspend fun updatePokemon(item: Pokemon) {
        pokemonsDao.insert(PokemonDb.fromDomainModel(item))
    }

    suspend fun removeAllPokemons() {
        pokemonsDao.deleteAll()
    }

    fun getAvailablePokemonSpecies(): Flow<List<PokemonSpecies>> {
        return pokemonSpeciesDao.getPokemonSpecies().map { it.toDomainModel() }
    }

}