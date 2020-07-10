package com.flowpreviewapplication.data.datasource.pokemon.remote

import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.model.PokemonSpecies
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class PokemonsRemoteDataSource {

    fun getPokemonsInfo(): Flow<Pokemon> {
        return flow {
            emit(
                Pokemon(
                    id = 10001,
                    name = "Pokemon from the Remote DataSource",
                    level = 1,
                    species = PokemonSpecies(
                        speciesName = "UNKNOWN",
                        imageUrl = null
                    )
                )
            )
            emit(
                Pokemon(
                    id = 10002,
                    name = "Another Pokemon from the Remote DataSource",
                    level = 2,
                    species = PokemonSpecies(
                        speciesName = "UNKNOWN",
                        imageUrl = null
                    )
                )
            )
        }.onStart { delay(2000) }
    }

}