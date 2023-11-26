package com.pieterv.uncaught.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.pieterv.uncaught.R
import com.pieterv.uncaught.data.model.PokedexListEntry
import com.pieterv.uncaught.presentation.component.ImageLoader
import com.pieterv.uncaught.presentation.component.PokemonCaughtIndicator
import kotlinx.coroutines.flow.flowOf

@Composable
fun PokemonListScreen(navController: NavController, viewModel: MainViewModel) {
    PokemonListScreenContent(
        viewModel.state,
        viewModel.pokedexEntries.collectAsLazyPagingItems()
    ) {
        viewModel.onEvent(it)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(MainScreenEvent.LoadPokemon)
    }
}

@Preview
@Composable
fun PokemonListPreview() {
    PokemonListScreenContent(MainScreenState()) {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreenContent(
    state: MainScreenState,
    entries: LazyPagingItems<PokedexListEntry> = flowOf(PagingData.empty<PokedexListEntry>()).collectAsLazyPagingItems(),
    onScreenEvent: (MainScreenEvent) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (state.failedLoading) {
                        //todo add failed loading state
                    } else {
                        PokemonList(pokedexEntries = entries, event = onScreenEvent)
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    pokedexEntries: LazyPagingItems<PokedexListEntry>,
    event: (MainScreenEvent) -> Unit
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(
            count = pokedexEntries.itemCount,
            key = pokedexEntries.itemKey { it.number },
        ) { index ->
            val item = pokedexEntries[index]
            if (item != null) {
                PokedexEntry(entry = item, event = event)
            }
        }
    }
}

@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    modifier: Modifier = Modifier,
    event: (MainScreenEvent) -> Unit
) {
    Box(
        contentAlignment = Center,
        modifier = modifier
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                event(MainScreenEvent.PokedexTap(entry))
            }
    ) {
        Column(horizontalAlignment = CenterHorizontally) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .diskCachePolicy(
                        CachePolicy.ENABLED
                    )
                    .crossfade(true)
                    .build(),
                loading = {
                    ImageLoader()
                },
                contentDescription = entry.pokemonName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = entry.pokemonName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = entry.number,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (entry.isCaught) {
            PokemonCaughtIndicator(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 8.dp,
                        end = 8.dp
                    )
            )
        }
    }
}