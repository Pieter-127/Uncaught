package com.pieterv.uncaught.di

import android.content.Context
import androidx.room.Room
import com.pieterv.uncaught.data.db.PokemonDb
import com.pieterv.uncaught.data.db.PokemonListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext appContext: Context): PokemonDb {
        return Room.databaseBuilder(
            appContext,
            PokemonDb::class.java,
            "pokemon_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePokedexTable(db: PokemonDb): PokemonListDao {
        return db.pokemonListDao()
    }
}
