package com.pieterv.uncaught.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.domain.usecase.LoadPokemonListUseCase
import com.pieterv.uncaught.domain.usecase.ToggleCaughtStatusUseCase
import com.pieterv.uncaught.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val loadPokemonListUseCase: LoadPokemonListUseCase,
    val toggleCaughtStatusUseCase: ToggleCaughtStatusUseCase
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    var pokedexEntries: MutableStateFlow<PagingData<PokedexListEntry>> =
        MutableStateFlow(value = PagingData.empty())
        private set

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.LoadPokemon -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val response = loadPokemonListUseCase(20)
                    if (response is Resource.Success) {
                        state = state.copy(failedLoading = false)
                        response.data?.cachedIn(viewModelScope)?.collectLatest {
                            pokedexEntries.value = it
                        }
                    } else {
                        state = state.copy(failedLoading = true)
                    }
                }
            }

            is MainScreenEvent.PokedexTap -> {
                //todo new screen
            }
        }
    }
}