package com.flowpreviewapplication.data.datasource.pokemon.local.model

import androidx.room.*
import com.flowpreviewapplication.domain.model.Pokemon

@Entity(
    tableName = "pokemons"
//    indices = [Index("id"), Index("species_id")],
//    foreignKeys = [
//        ForeignKey(
//            entity = PokemonSpeciesDb::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("species_id"),
//            deferred = true,
//            onDelete = ForeignKey.SET_NULL
//        )
//    ]
)
data class PokemonDb(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "level") var level: Int,
    @ColumnInfo(name = "species_id") var species_id: Long? = null
) {
    companion object {

        fun fromDomainModel(item: Pokemon): PokemonDb = PokemonDb(
            id = item.id,
            name = item.name,
            level = item.level,
            species_id = item.species?.id
        )

    }
}

class PokemonInfoDb {
    @Embedded
    lateinit var pokemon: PokemonDb

    @Relation(
        parentColumn = "species_id",
        entityColumn = "id"
    )
    var species: PokemonSpeciesDb? = null
}

fun PokemonInfoDb.toDomainModel(): Pokemon = Pokemon(
    id = this.pokemon.id,
    name = this.pokemon.name,
    level = this.pokemon.level,
    species = this.species?.toDomainModel()
)

fun List<PokemonInfoDb>.toDomainModel(): List<Pokemon> = map { it.toDomainModel() }
