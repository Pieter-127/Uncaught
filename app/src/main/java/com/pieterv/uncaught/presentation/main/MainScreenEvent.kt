package com.pieterv.uncaught.presentation.main

import com.pieterv.uncaught.data.model.PokedexListEntry

sealed class MainScreenEvent {
    data class PokedexTap(val pokemon: PokedexListEntry) : MainScreenEvent()
    data object LoadPokemon : MainScreenEvent()
}