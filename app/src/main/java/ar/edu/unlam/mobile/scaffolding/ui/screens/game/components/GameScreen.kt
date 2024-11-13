package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import ar.edu.unlam.mobile.scaffolding.R

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GameScreenPreview(
    onDrawCard: () -> Unit = {},
    triggerAnim: Boolean = false,
) {
    var playerCardCoordinates by remember { mutableStateOf(Offset.Zero) }
    var rivalCardCoordinates by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.matchParentSize()) {
            RivalSideBoard(
                modifier =
                    Modifier
                        .weight(1f),
                onPlaced = { coordinates ->
                    rivalCardCoordinates = coordinates
                },
            )
            PlayerSideBoard(
                modifier =
                    Modifier
                        .weight(1f),
                imageBitmap =
                    ResourcesCompat
                        .getDrawable(
                            LocalContext.current.resources,
                            R.drawable.default_player_background,
                            null,
                        )!!
                        .toBitmap()
                        .asImageBitmap(),
                onPlaced = { coordinates ->
                    playerCardCoordinates = coordinates
                },
            )
        }
        DicesAndDeck(
            modifier =
                Modifier
                    .align(Alignment.Center),
            triggerDrawCardAnim = triggerAnim,
            onDrawCard = onDrawCard,
            playerCardCoordinates = playerCardCoordinates,
            rivalCardCoordinates = rivalCardCoordinates

        )
    }
}
