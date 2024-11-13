package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.ui.components.getCardImage
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.GameViewModel
import ar.edu.unlam.mobile.scaffolding.ui.utils.toImageBitmap
import kotlinx.coroutines.delay

@Composable
fun GameScreenPreview(
    viewModel: GameViewModel = hiltViewModel(),
    onGameEnd: () -> Unit = {},
) {
    var playerCardCoordinates by remember { mutableStateOf(Offset.Zero) }
    var rivalCardCoordinates by remember { mutableStateOf(Offset.Zero) }
    var deckCoordinates by remember { mutableStateOf(Offset.Zero) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        viewModel.startGame(maxRounds = 10)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GameBoard(
            modifier = Modifier.matchParentSize(),
            roundNumber = { state.currentRound },
            userImage =
                state.userImage?.toImageBitmap() ?: ResourcesCompat
                    .getDrawable(
                        LocalContext.current.resources,
                        R.drawable.default_player_background,
                        null,
                    )!!
                    .toBitmap()
                    .asImageBitmap(),
            userPoints = state.playerPoints,
            rivalPoints = state.cpuPoints,
            deckCoordinates = { deckCoordinates = it },
            playerCardCoordinates = { playerCardCoordinates = it },
            rivalCardCoordinates = { rivalCardCoordinates = it },
        )
        Box(modifier = Modifier.padding(16.dp).align(Alignment.BottomStart).fillMaxWidth()) {
            PlayingCardWithAnimation(
                modifier = Modifier.align(Alignment.BottomStart),
                card = getCardImage(state.rivalCard ?: PlayCard()),
                targetValueX = with(density) { rivalCardCoordinates.x.toDp().value - deckCoordinates.x.toDp().value },
                targetValueY = with(density) { rivalCardCoordinates.y.toDp().value - deckCoordinates.y.toDp().value },
                triggerAnim = !state.isPlayerTurn && state.isDrawingCard,
                deckCoordinates = deckCoordinates,
            )
        }
        Box(modifier = Modifier.padding(16.dp).align(Alignment.BottomStart).fillMaxWidth()) {
            PlayingCardWithAnimation(
                card = getCardImage(state.playerCard ?: PlayCard()),
                targetValueX = with(density) { playerCardCoordinates.x.toDp().value - deckCoordinates.x.toDp().value },
                targetValueY = with(density) { playerCardCoordinates.y.toDp().value - deckCoordinates.y.toDp().value },
                triggerAnim = state.isPlayerTurn && state.isDrawingCard,
                deckCoordinates = deckCoordinates,
            )
        }
        Column(
            modifier =
                Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DicePair(
                diceOne = state.dicePair.first,
                diceTwo = state.dicePair.second,
                modifier = Modifier.size(80.dp),
            )
            Button(
                onClick = viewModel::playerThrowDices,
                enabled = state.throwButtonEnabled && !state.gameOver && state.isPlayerTurn,
            ) {
                Text("Tirar dados")
            }
        }
        if (state.gameOver) {
            Text(
                text = "Ganador: ${state.winner}",
                fontSize = 24.sp,
                color = Color.Green,
            )
            LaunchedEffect(Unit) {
                delay(2000)
                onGameEnd()
            }
        }
    }
}
