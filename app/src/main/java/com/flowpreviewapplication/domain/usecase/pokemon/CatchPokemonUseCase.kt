package com.flowpreviewapplication.domain.usecase.pokemon

import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.repository.pokemon.PokemonsInfoRepository
import kotlinx.coroutines.flow.first
import kotlin.random.Random

class CatchPokemonUseCase(private val pokemonsInfoRepository: PokemonsInfoRepository) {

    suspend fun execute(): Pokemon {
        val newPokemon = getRandomPokemon()
        pokemonsInfoRepository.addPokemon(newPokemon)
        return newPokemon
    }

    private suspend fun getRandomPokemon(): Pokemon {
        val pokemonsSpecies = pokemonsInfoRepository.getAvailablePokemonSpecies().first()
        val randomPokemonSpecies = pokemonsSpecies.random()
        return Pokemon(
            name = "${randomPokemonSpecies.speciesName} ${Random.nextInt(
                'A'.toInt(),
                'Z'.toInt() + 1
            ).toChar()}",
            level = Random.nextInt(1, 25),
            species = randomPokemonSpecies
        )
    }

}