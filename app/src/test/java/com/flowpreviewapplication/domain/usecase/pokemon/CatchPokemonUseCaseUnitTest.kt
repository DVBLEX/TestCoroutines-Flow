package com.flowpreviewapplication.domain.usecase.pokemon

import com.flowpreviewapplication.domain.model.PokemonSpecies
import com.flowpreviewapplication.domain.repository.pokemon.PokemonsInfoRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CatchPokemonUseCaseUnitTest {

    private lateinit var pokemonsInfoRepository: PokemonsInfoRepository
    private lateinit var useCase: CatchPokemonUseCase

    @Before
    fun setUp() {
        pokemonsInfoRepository = mock()
        useCase = CatchPokemonUseCase(pokemonsInfoRepository)
    }

    @Test
    fun `when executed than it should catch random pokemon of the species that retrieved from the repository`() =
        runBlockingTest {
            // Given
            val testSpecies = PokemonSpecies(speciesName = "Test", imageUrl = null)
            whenever(pokemonsInfoRepository.getAvailablePokemonSpecies()).thenReturn(
                flowOf(
                    listOf(
                        testSpecies
                    )
                )
            )

            // When
            val result = useCase.execute()

            // Then
            verify(pokemonsInfoRepository, times(1)).getAvailablePokemonSpecies()
            verify(pokemonsInfoRepository, times(1)).addPokemon(result)
            assertEquals(result.species, testSpecies)
        }

}