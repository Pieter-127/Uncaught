package com.pieterv.uncaught.domain.repository

import androidx.paging.PagingData
import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.data.paging.PokemonPagingSource
import com.pieterv.uncaught.data.remote.PokemonDto
import com.pieterv.uncaught.data.remote.PokemonListDto
import com.pieterv.uncaught.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonPagingData(initialPageSize: Int): Resource<Flow<PagingData<PokedexListEntry>>>

    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonDto>

    suspend fun updatePokedexEntry(pokedexListEntry: PokedexListEntry): Resource<Boolean>
}

