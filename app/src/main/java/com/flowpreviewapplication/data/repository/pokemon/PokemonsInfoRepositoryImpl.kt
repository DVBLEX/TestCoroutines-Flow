package com.flowpreviewapplication.data.repository.pokemon

import com.flowpreviewapplication.data.datasource.pokemon.local.PokemonsLocalDataSource
import com.flowpreviewapplication.data.datasource.pokemon.remote.PokemonsRemoteDataSource
import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.model.PokemonSpecies
import com.flowpreviewapplication.domain.repository.pokemon.PokemonsInfoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class PokemonsInfoRepositoryImpl(
    private val remoteDataSource: PokemonsRemoteDataSource,
    private val localDataSource: PokemonsLocalDataSource
) : PokemonsInfoRepository {

    override fun getPokemonsInfo(): Flow<List<Pokemon>> {
        return localDataSource.getPokemonsInfo()
    }

    override fun getAvailablePokemonSpecies(): Flow<List<PokemonSpecies>> {
        return localDataSource.getAvailablePokemonSpecies()
    }

    override suspend fun updatePokemon(item: Pokemon) {
        localDataSource.updatePokemon(item)
    }

    override suspend fun addPokemon(item: Pokemon) {
        delay(1000)
        localDataSource.addPokemon(item)
    }

    override suspend fun removeAllPokemons() {
        localDataSource.removeAllPokemons()
    }

}