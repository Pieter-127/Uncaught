package com.pieterv.uncaught.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pieterv.uncaught.data.db.PokemonDb
import com.pieterv.uncaught.data.mapper.toPokedexEntry
import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.data.remote.PokemonApi

class PokemonPagingSource(
    private val api: PokemonApi,
    private val db: PokemonDb
) : PagingSource<Int, PokedexListEntry>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokedexListEntry> {
        return try {
            val nextPageNumber = params.key ?: 0
            val pageSize = PAGE_SIZE

            val response = fetchPokemon(nextPageNumber, pageSize)

            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun fetchPokemon(
        pageNumber: Int,
        pageSize: Int
    ): List<PokedexListEntry> {
        val offset = pageNumber * pageSize
        val pokemonInDb = db.pokemonListDao().getPokemonRange(pageSize, offset)

        return pokemonInDb.ifEmpty {
            val pokedexEntries = pokedexListEntries(pageSize, offset)
            db.pokemonListDao().insertAll(pokedexEntries)
            pokedexEntries
        }
    }

    private suspend fun pokedexListEntries(
        pageSize: Int,
        offset: Int
    ): ArrayList<PokedexListEntry> {
        val responseFromApi = api.getPokemonList(pageSize, offset)
        return responseFromApi.toPokedexEntry()
    }

    override fun getRefreshKey(state: PagingState<Int, PokedexListEntry>): Int? {
        return null
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}


