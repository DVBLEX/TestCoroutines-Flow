package com.flowpreviewapplication.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.flowpreviewapplication.data.datasource.pokemon.local.PokemonSpeciesDao
import com.flowpreviewapplication.data.datasource.pokemon.local.PokemonsDao
import com.flowpreviewapplication.data.datasource.pokemon.local.model.PokemonDb
import com.flowpreviewapplication.data.datasource.pokemon.local.model.PokemonSpeciesDb
import com.flowpreviewapplication.domain.model.PokemonSpecies
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


@Database(entities = [PokemonDb::class, PokemonSpeciesDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonsDao

    abstract fun pokemonSpeciesDao(): PokemonSpeciesDao

    companion object {

        var dbCallback: Callback = object : Callback(), KoinComponent {

            val database: AppDatabase by inject()

            override fun onCreate(db: SupportSQLiteDatabase) {
                val pokemonSpecies = listOf(
                    PokemonSpecies(
                        speciesName = "Bulbasaur",
                        imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png"
                    ),
                    PokemonSpecies(
                        speciesName = "Charmander",
                        imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png"
                    ),
                    PokemonSpecies(
                        speciesName = "Squirtle",
                        imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/007.png"
                    ),
                    PokemonSpecies(
                        speciesName = "Caterpie",
                        imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/010.png"
                    ),
                    PokemonSpecies(
                        speciesName = "Weedle",
                        imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/013.png"
                    )
                ).map {
                    PokemonSpeciesDb.fromDomainModel(it)
                }

                GlobalScope.launch {
                    database.pokemonSpeciesDao().insert(pokemonSpecies)
                }
            }
        }
    }
}