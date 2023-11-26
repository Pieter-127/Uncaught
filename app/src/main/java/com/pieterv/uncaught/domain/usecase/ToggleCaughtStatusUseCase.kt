package com.pieterv.uncaught.domain.usecase

import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.domain.util.Resource

interface ToggleCaughtStatusUseCase {

    suspend operator fun invoke(pokedexListEntry: PokedexListEntry): Resource<Boolean>
}