package com.pieterv.uncaught.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pieterv.uncaught.data.db.PokemonListDao
import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.data.paging.PokemonPagingSource
import com.pieterv.uncaught.data.remote.PokemonApi
import com.pieterv.uncaught.data.remote.PokemonDto
import com.pieterv.uncaught.data.remote.PokemonListDto
import com.pieterv.uncaught.domain.repository.PokemonRepository
import com.pieterv.uncaught.domain.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
    private val table: PokemonListDao,
    private val pokemonPagingSource: PokemonPagingSource,
) : PokemonRepository {

    override suspend fun getPokemonPagingData(initialPageSize: Int): Resource<Flow<PagingData<PokedexListEntry>>> {
        return Resource.Success(Pager(config = PagingConfig(
            pageSize = initialPageSize,
            enablePlaceholders = true
        ),
            pagingSourceFactory = { pokemonPagingSource }).flow
        )
    }

    override suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonDto> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error(e)
        }
        return Resource.Success(response)
    }

    override suspend fun updatePokedexEntry(pokedexListEntry: PokedexListEntry): Resource<Boolean> {
        try {
            table.updatePokedexEntry(pokedexListEntry)
        } catch (e: Exception) {
            return Resource.Error(e)
        }

        return Resource.Success(true)
    }
}