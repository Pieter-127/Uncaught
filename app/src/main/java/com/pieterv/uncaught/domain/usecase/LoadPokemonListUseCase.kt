package com.pieterv.uncaught.domain.usecase

import androidx.paging.PagingData
import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoadPokemonListUseCase {

     suspend operator fun invoke(initialPageSize: Int): Resource<Flow<PagingData<PokedexListEntry>>>
}