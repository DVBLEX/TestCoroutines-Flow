package com.flowpreviewapplication.domain.usecase.pokemon

import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.repository.pokemon.PokemonsInfoRepository
import kotlin.math.min

class IncreasePokemonLevelUseCase(private val pokemonsInfoRepository: PokemonsInfoRepository) {

    suspend fun execute(item: Pokemon) =
        pokemonsInfoRepository.updatePokemon(
            item.copy(
                level = min(
                    item.level + 1,
                    POKEMON_MAX_LEVEL
                )
            )
        )

    companion object {
        private const val POKEMON_MAX_LEVEL = 25
    }

}