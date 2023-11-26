package com.pieterv.uncaught.di

import com.pieterv.uncaught.data.db.PokemonDb
import com.pieterv.uncaught.data.db.PokemonListDao
import com.pieterv.uncaught.data.paging.PokemonPagingSource
import com.pieterv.uncaught.data.remote.PokemonApi
import com.pieterv.uncaught.data.repository.PokemonRepositoryImpl
import com.pieterv.uncaught.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: PokemonApi,
        table: PokemonListDao,
        pokemonPagingSource: PokemonPagingSource
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, table, pokemonPagingSource)
    }


    @Provides
    @Singleton
    fun providePokemonPagingSource(api: PokemonApi, database: PokemonDb): PokemonPagingSource {
        return PokemonPagingSource(api, database)
    }

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
            .create()
    }
}