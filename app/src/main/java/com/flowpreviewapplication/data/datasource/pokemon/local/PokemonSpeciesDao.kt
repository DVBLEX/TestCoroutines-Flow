package com.flowpreviewapplication.data.datasource.pokemon.local

import androidx.room.*
import com.flowpreviewapplication.data.datasource.pokemon.local.model.PokemonSpeciesDb
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonSpeciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: PokemonSpeciesDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: List<PokemonSpeciesDb>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg obj: PokemonSpeciesDb)

    @Delete
    suspend fun delete(vararg obj: PokemonSpeciesDb)

    @Query("SELECT * FROM pokemon_species")
    fun getPokemonSpecies(): Flow<List<PokemonSpeciesDb>>

}