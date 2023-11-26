package com.pieterv.uncaught.domain.usecase.impl

import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.domain.repository.PokemonRepository
import com.pieterv.uncaught.domain.usecase.ToggleCaughtStatusUseCase
import com.pieterv.uncaught.domain.util.Resource
import javax.inject.Inject

class ToggleCaughtStatusUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ToggleCaughtStatusUseCase {

    override suspend fun invoke(pokedexListEntry: PokedexListEntry): Resource<Boolean> {
        return pokemonRepository.updatePokedexEntry(pokedexListEntry.copy(isCaught = !pokedexListEntry.isCaught))
    }
}