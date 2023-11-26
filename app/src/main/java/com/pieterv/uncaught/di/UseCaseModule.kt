package com.pieterv.uncaught.di

import com.pieterv.uncaught.domain.repository.PokemonRepository
import com.pieterv.uncaught.domain.usecase.LoadPokemonListUseCase
import com.pieterv.uncaught.domain.usecase.ToggleCaughtStatusUseCase
import com.pieterv.uncaught.domain.usecase.impl.LoadPokemonListUseCaseImpl
import com.pieterv.uncaught.domain.usecase.impl.ToggleCaughtStatusUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun loadPokemonListUseCase(pokemonRepository: PokemonRepository): LoadPokemonListUseCase {
        return LoadPokemonListUseCaseImpl(pokemonRepository)
    }

    @Provides
    @Singleton
    fun updatePokedexUseCase(pokemonRepository: PokemonRepository): ToggleCaughtStatusUseCase {
        return ToggleCaughtStatusUseCaseImpl(pokemonRepository)
    }
}