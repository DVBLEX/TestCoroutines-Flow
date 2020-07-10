package com.flowpreviewapplication.ui.pokemon.list

import androidx.lifecycle.*
import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.usecase.pokemon.CatchPokemonUseCase
import com.flowpreviewapplication.domain.usecase.pokemon.GetPokemonsInfoUseCase
import com.flowpreviewapplication.domain.usecase.pokemon.IncreasePokemonLevelUseCase
import com.flowpreviewapplication.domain.usecase.pokemon.ReleaseAllPokemonsUseCase
import com.flowpreviewapplication.ui.pokemon.list.model.PokemonsListResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PokemonsListViewModel(
    private val getPokemonsInfoUseCase: GetPokemonsInfoUseCase,
    private val catchPokemonUseCase: CatchPokemonUseCase,
    private val increasePokemonLevelUseCase: IncreasePokemonLevelUseCase,
    private val releaseAllPokemonsUseCase: ReleaseAllPokemonsUseCase
) : ViewModel() {

    val pokemonsListLiveData: LiveData<PokemonsListResult> = liveData<PokemonsListResult> {
        emit(PokemonsListResult.Loading)
        emitSource(getPokemonsInfoUseCase.execute()
            .map<List<Pokemon>, PokemonsListResult> { PokemonsListResult.Success(it) }
            .catch { emit(PokemonsListResult.Error(it)) }
            .distinctUntilChanged()
            .asLiveData())
    }

//    val pokemonsListLiveDataAlternative: LiveData<PokemonsListResult> =
//        getPokemonsInfoUseCase.execute()
//            .map<List<Pokemon>, PokemonsListResult> { PokemonsListResult.Success(it) }
//            .onStart { emit(PokemonsListResult.Loading) }
//            .catch { emit(PokemonsListResult.Error(it)) }
//            .distinctUntilChanged()
//            .asLiveData()

    fun onPokemonSelected(item: Pokemon) = viewModelScope.launch {
        increasePokemonLevelUseCase.execute(item)
    }

    fun onCatchPokemonAction() = viewModelScope.launch {
        catchPokemonUseCase.execute()
    }

    fun onReleaseAllPokemonsAction() = viewModelScope.launch {
        releaseAllPokemonsUseCase.execute()
    }

}