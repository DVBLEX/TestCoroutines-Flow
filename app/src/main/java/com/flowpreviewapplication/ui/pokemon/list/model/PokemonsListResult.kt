package com.flowpreviewapplication.ui.pokemon.list.model

import com.flowpreviewapplication.domain.model.Pokemon

sealed class PokemonsListResult {

    object Loading : PokemonsListResult()

    data class Success(val items: List<Pokemon>) : PokemonsListResult()

    data class Error(val exception: Throwable) : PokemonsListResult()

}