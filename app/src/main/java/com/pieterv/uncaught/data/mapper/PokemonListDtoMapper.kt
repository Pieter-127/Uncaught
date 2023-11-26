package com.pieterv.uncaught.data.mapper

import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.data.remote.PokemonListDto
import com.pieterv.uncaught.data.remote.Result
import java.util.Locale

fun PokemonListDto.toPokedexEntry(): ArrayList<PokedexListEntry> {
    val pokedexEntries = results.map { entry ->
        val pokemonNumber = determinePokemonNumber(entry)
        val displayNumber = formatNumber(pokemonNumber)
        val url = formatImageUrl(pokemonNumber)
        val name = formatName(entry)
        val isCaught = false
        PokedexListEntry(
            pokemonName = name,
            imageUrl = url,
            number = displayNumber,
            isCaught = isCaught
        )
    }
    return ArrayList(pokedexEntries)
}

private fun formatName(entry: Result) = entry.name.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
}

private fun formatNumber(number: Int): String {
    //Format the number with leading zeroes if it's less than 100
    return String.format("#%03d", number)
}

private fun formatImageUrl(number: Int): String {
    val paddedNumber = String.format("%03d", number)
    return "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$paddedNumber.png"
}

private fun determinePokemonNumber(entry: Result): Int {
    // Example URL from response = https://pokeapi.co/api/v2/pokemon/1/
    // Extract the number part from the URL
    return entry.url.split("/").lastOrNull { it.isNotEmpty() }?.toIntOrNull() ?: 0
}