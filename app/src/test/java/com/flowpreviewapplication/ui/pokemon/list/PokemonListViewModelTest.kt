package com.flowpreviewapplication.ui.pokemon.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.domain.usecase.pokemon.CatchPokemonUseCase
import com.flowpreviewapplication.domain.usecase.pokemon.GetPokemonsInfoUseCase
import com.flowpreviewapplication.domain.usecase.pokemon.IncreasePokemonLevelUseCase
import com.flowpreviewapplication.domain.usecase.pokemon.ReleaseAllPokemonsUseCase
import com.flowpreviewapplication.ui.pokemon.list.model.PokemonsListResult
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PokemonListViewModelTest {

    private lateinit var getPokemonsInfoUseCase: GetPokemonsInfoUseCase
    private lateinit var catchPokemonUseCase: CatchPokemonUseCase
    private lateinit var increasePokemonLevelUseCase: IncreasePokemonLevelUseCase
    private lateinit var releaseAllPokemonsUseCase: ReleaseAllPokemonsUseCase

    private lateinit var viewModel: PokemonsListViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        getPokemonsInfoUseCase = mock()
        catchPokemonUseCase = mock()
        increasePokemonLevelUseCase = mock()
        releaseAllPokemonsUseCase = mock()

        viewModel = PokemonsListViewModel(
            getPokemonsInfoUseCase,
            catchPokemonUseCase,
            increasePokemonLevelUseCase,
            releaseAllPokemonsUseCase
        )
    }

    @Test
    fun `when loaded than the list of pokemons should be loaded`() = runBlockingTest {
        // Given
        val pokemonsList = getMockedPokemons()
        whenever(getPokemonsInfoUseCase.execute()).thenReturn(flowOf(pokemonsList))

        // When
        setUpEventObserver { observer ->

            // Then
            verify(observer, times(2)).onChanged(capture())
            assertEquals(PokemonsListResult.Loading, firstValue)
            assertEquals(PokemonsListResult.Success(pokemonsList), secondValue)
            verify(getPokemonsInfoUseCase, times(1)).execute()
        }
    }

    @Test
    fun `when loaded with error than the error state should be loaded`() = runBlockingTest {
        // Given
        val error = Exception("Test Error")
        whenever(getPokemonsInfoUseCase.execute()).thenReturn(flow { throw error })

        // When
        setUpEventObserver { observer ->

            // Then
            verify(observer, times(2)).onChanged(capture())
            assertEquals(PokemonsListResult.Loading, firstValue)
            assertEquals(PokemonsListResult.Error(error), secondValue)
            verify(getPokemonsInfoUseCase, times(1)).execute()
        }
    }

    @Test()
    fun `whenever catch pokemon action is triggered then pokemons list should be updated`() =
        runBlockingTest {
            // Given
            val pokemonsList = getMockedPokemons()
            val newPokemonsList = pokemonsList.toMutableList().apply {
                add(getMockedPokemon())
            }.toList()

            val pokemonsChannel = Channel<List<Pokemon>>(1)

            pokemonsChannel.send(pokemonsList)
            whenever(getPokemonsInfoUseCase.execute()).thenReturn(pokemonsChannel.receiveAsFlow())
            whenever(catchPokemonUseCase.execute()).thenAnswer {
                pokemonsChannel.offer(
                    newPokemonsList
                )
            }
            setUpEventObserver { observer ->
                reset(observer)
                reset(getPokemonsInfoUseCase)

                // When
                viewModel.onCatchPokemonAction()

                // Then
                verify(observer, times(1)).onChanged(capture())
                assertEquals(PokemonsListResult.Success(newPokemonsList), firstValue)
                verify(getPokemonsInfoUseCase, never()).execute()
                verify(catchPokemonUseCase, times(1)).execute()
            }
        }

    private fun getMockedPokemons(): List<Pokemon> {
        return listOf(
            Pokemon(name = "Test 1", level = 1, species = null),
            Pokemon(name = "Test 2", level = 2, species = null)
        )
    }

    private fun getMockedPokemon(): Pokemon {
        return Pokemon(name = "Test 3", level = 10, species = null)
    }

    private inline fun setUpEventObserver(action: KArgumentCaptor<PokemonsListResult>.(Observer<PokemonsListResult>) -> Unit) {
        val observer: Observer<PokemonsListResult> = mock()
        viewModel.pokemonsListLiveData.observeForever(observer)
        argumentCaptor<PokemonsListResult> {
            action(observer)
        }
        viewModel.pokemonsListLiveData.removeObserver(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}