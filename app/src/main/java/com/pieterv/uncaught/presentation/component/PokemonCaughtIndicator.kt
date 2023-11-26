package com.pieterv.uncaught.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pieterv.uncaught.R

@Composable
fun PokemonCaughtIndicator(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = stringResource(R.string.poke_ball),
            modifier = Modifier.size(30.dp)
        )
    }
}