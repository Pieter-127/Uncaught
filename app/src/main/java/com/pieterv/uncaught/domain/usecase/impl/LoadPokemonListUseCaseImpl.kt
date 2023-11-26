package com.pieterv.uncaught.domain.usecase.impl

import androidx.paging.PagingData
import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.domain.repository.PokemonRepository
import com.pieterv.uncaught.domain.usecase.LoadPokemonListUseCase
import com.pieterv.uncaught.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadPokemonListUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : LoadPokemonListUseCase {

    override suspend operator fun invoke(initialPageSize: Int): Resource<Flow<PagingData<PokedexListEntry>>> {
        return pokemonRepository.getPokemonPagingData(initialPageSize)
    }
}