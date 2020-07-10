package com.flowpreviewapplication.domain.usecase.pokemon

import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.repository.pokemon.PokemonsInfoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.lang.Exception

class GetPokemonsInfoUseCase(private val pokemonsInfoRepository: PokemonsInfoRepository) {

    fun execute(): Flow<List<Pokemon>> = pokemonsInfoRepository.getPokemonsInfo()

}