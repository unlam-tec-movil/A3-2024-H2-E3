package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
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
    deckCoordinates: (Offset) -> Unit,
    playerCardCoordinates: (Offset) -> Unit,
    rivalCardCoordinates: (Offset) -> Unit,
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.matchParentSize()) {
            RivalSideBoard(
                modifier = Modifier.weight(1f),
                points = 0,
                onPlaced = { rivalCardCoordinates(it) },
            )
            PlayerSideBoard(
                modifier = Modifier.weight(1f),
                userName = "",
                points = 0,
                userImage = userImage,
                onPlaced = { playerCardCoordinates(it) },
            )
        }
        CardDeck(
            modifier =
                Modifier
                    .padding(16.dp)
                    .size(96.dp)
                    .align(Alignment.BottomStart)
                    .onGloballyPositioned { coordinates ->
                        deckCoordinates(coordinates.positionOnScreen())
                    },
        )
        Text(
            text = stringResource(R.string.ronda, roundNumber()),
            modifier = Modifier.padding(16.dp).align(Alignment.BottomCenter),
            color = Color.White,
            fontSize = 36.sp,
        )
    }
}
