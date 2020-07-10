package com.flowpreviewapplication.data.datasource.pokemon.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flowpreviewapplication.domain.model.PokemonSpecies

@Entity(tableName = "pokemon_species")
data class PokemonSpeciesDb(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "species_name") var speciesName: String,
    @ColumnInfo(name = "image_url") var imageUrl: String?
) {

    companion object {

        fun fromDomainModel(item: PokemonSpecies): PokemonSpeciesDb = PokemonSpeciesDb(
            id = item.id,
            speciesName = item.speciesName,
            imageUrl = item.imageUrl
        )

    }
}

fun PokemonSpeciesDb.toDomainModel(): PokemonSpecies = PokemonSpecies(
    id = this.id,
    speciesName = this.speciesName,
    imageUrl = this.imageUrl
)

fun List<PokemonSpeciesDb>.toDomainModel(): List<PokemonSpecies> = map { it.toDomainModel() }
