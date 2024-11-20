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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.Dice
import ar.edu.unlam.mobile.scaffolding.ui.components.StatusMessage
import ar.edu.unlam.mobile.scaffolding.ui.components.getCardImage
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.GameViewModel
import ar.edu.unlam.mobile.scaffolding.ui.utils.Routes
import ar.edu.unlam.mobile.scaffolding.ui.utils.toImageBitmap
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    onGameEnd: (String) -> Unit = {},
) {
    var playerCardCoordinates by remember { mutableStateOf(Offset.Zero) }
    var rivalCardCoordinates by remember { mutableStateOf(Offset.Zero) }
    var deckCoordinates by remember { mutableStateOf(Offset.Zero) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val density = LocalDensity.current

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
        Box(
            modifier =
                Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
        ) {
            PlayingCardWithAnimation(
                modifier = Modifier.align(Alignment.BottomStart),
                card = getCardImage(state.rivalCard),
                targetValueX = with(density) { rivalCardCoordinates.x.toDp().value - deckCoordinates.x.toDp().value },
                targetValueY = with(density) { rivalCardCoordinates.y.toDp().value - deckCoordinates.y.toDp().value },
                triggerAnim = !state.isPlayerTurn && state.isDrawingCard,
            )
        }
        Box(
            modifier =
                Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
        ) {
            PlayingCardWithAnimation(
                card = getCardImage(state.playerCard),
                targetValueX = with(density) { playerCardCoordinates.x.toDp().value - deckCoordinates.x.toDp().value },
                targetValueY = with(density) { playerCardCoordinates.y.toDp().value - deckCoordinates.y.toDp().value },
                triggerAnim = state.isPlayerTurn && state.isDrawingCard,
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
            Dice(
                dice = state.dice,
                modifier = Modifier.testTag("Dice").size(80.dp),
            )
            Button(
                onClick = viewModel::playerThrowDices,
                enabled = state.throwButtonEnabled && !state.gameOver && state.isPlayerTurn,
            ) {
                Text("Tirar dados")
            }
        }
        if (state.showStatusMessage) {
            Box(
                modifier =
                    Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
            ) {
                StatusMessage(
                    isPlayerTurn = state.isPlayerTurn,
                    statusMesssage = state.statusMessage,
                )
            }
        }
        if (state.gameOver) {
            Box(
                modifier =
                    Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
            ) {
                StatusMessage(
                    isPlayerTurn = state.isPlayerTurn,
                    statusMesssage = "Fin del juego, el ganador es: ${state.winner}",
                )
            }
            LaunchedEffect(Unit) {
                delay(2000)
                if (state.winner == "Jugador") {
                    onGameEnd(Routes.MENU_ROUTE)
                } else {
                    onGameEnd(Routes.MAP_ROUTE)
                }
            }
        }
    }
}
