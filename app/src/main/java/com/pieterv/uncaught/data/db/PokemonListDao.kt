package com.pieterv.uncaught.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pieterv.uncaught.data.model.PokedexListEntry

@Dao
interface PokemonListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokedexListEntry>)

    @Query("SELECT * FROM pokedex LIMIT :pageSize OFFSET :offset")
    suspend fun getPokemonRange(pageSize: Int, offset: Int): List<PokedexListEntry>

    @Update(entity = PokedexListEntry::class)
    suspend fun updatePokedexEntry(update: PokedexListEntry)
}