package com.flowpreviewapplication.domain.usecase.pokemon

import com.flowpreviewapplication.domain.repository.pokemon.PokemonsInfoRepository

class ReleaseAllPokemonsUseCase(private val pokemonsInfoRepository: PokemonsInfoRepository) {

    suspend fun execute() = pokemonsInfoRepository.removeAllPokemons()

}