package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CardDeck

@Composable
fun GameBoard(
    modifier: Modifier = Modifier,
    roundNumber: () -> Int = { 1 },
    userImage: ImageBitmap,
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.matchParentSize()) {
            RivalSideBoard(modifier = Modifier.weight(1f))
            PlayerSideBoard(
                modifier = Modifier.weight(1f),
                userName = "",
                image = userImage,
            )
        }
        CardDeck(
            modifier =
            Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp),
        )
        Text(
            text = stringResource(R.string.ronda, roundNumber()),
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = 24.sp,
        )
    }
}