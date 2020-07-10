package com.flowpreviewapplication.data.datasource.pokemon.local

import androidx.room.*
import com.flowpreviewapplication.data.datasource.pokemon.local.model.PokemonDb
import com.flowpreviewapplication.data.datasource.pokemon.local.model.PokemonInfoDb
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: PokemonDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: List<PokemonDb>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg obj: PokemonDb)

    @Delete
    suspend fun delete(vararg obj: PokemonDb)

    @Query("DELETE FROM pokemons")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM pokemons")
    fun getPokemonsInfo(): Flow<List<PokemonInfoDb>>

    @Transaction
    @Query("SELECT * FROM pokemons")
    fun getPokemonsInfoOld(): List<PokemonInfoDb>

}