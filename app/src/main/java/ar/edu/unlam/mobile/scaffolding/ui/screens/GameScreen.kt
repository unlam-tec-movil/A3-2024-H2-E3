package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.ui.components.CardDeck
import ar.edu.unlam.mobile.scaffolding.ui.components.Dice
import ar.edu.unlam.mobile.scaffolding.ui.components.PlayCard
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startGame(maxRounds = 10)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text("Ronda ${state.currentRound}")
        if (state.isPlayerTurn) {
            Text("Es tu turno")
        } else {
            Text("Es el turno de la CPU")
        }

        Text("Jugador: ${state.playerPoints} - CPU: ${state.cpuPoints}")

        Text("Sumaste: ${state.diceThrowResult}")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Dice(dice = state.dicePair.first, modifier = Modifier.size(80.dp))
            Dice(dice = state.dicePair.second, modifier = Modifier.size(80.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            state.playerCard?.let { card ->
                CardView(card = card)
            }
            state.rivalCard?.let { card ->
                CardView(card = card)
            }
        }

        Button(
            onClick = viewModel::playerDrawCard,
            enabled = !state.gameOver && state.isPlayerTurn,
        ) {
            Text("Sacar carta")
        }

        Button(
            onClick = viewModel::playerThrowDices,
            enabled = state.throwButtonEnabled && !state.gameOver && state.isPlayerTurn,
        ) {
            Text("Tirar dados")
        }

        if (state.gameOver) {
            Text(
                text = "Ganador: ${state.winner}",
                fontSize = 24.sp,
                color = Color.Green,
            )
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver al inicio")
            }
        }
    }
}

@Composable
fun CardView(card: PlayCard) {
    Box(
        modifier =
            Modifier
                .size(100.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Carta: ${card.value.numericValue} + ${card.type}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GameScreenPreview(onDrawCard: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.matchParentSize()) {
            RivalSideBoard(
                modifier =
                    Modifier
                        .weight(1f),
                false,
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
                false,
            )
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CardDeck(modifier = Modifier.size(96.dp))
                Dice(
                    dice = ar.edu.unlam.mobile.scaffolding.domain.models.Dice.ONE,
                    modifier = Modifier.size(80.dp),
                )
                Dice(
                    dice = ar.edu.unlam.mobile.scaffolding.domain.models.Dice.SIX,
                    modifier = Modifier.size(80.dp),
                )
            }
            Button(
                onClick = { onDrawCard() },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text("Sacar carta")
            }
        }
    }
}

@Composable
fun PlayingCardWithAnimation(
    modifier: Modifier = Modifier,
    card: Int,
    initValueX: Float = 0f,
    targetValueX: Float,
    initValueY: Float = 0f,
    targetValueY: Float,
    triggerAnim: Boolean,
) {
    val offsetX = remember { Animatable(initValueX) }
    val offsetY = remember { Animatable(initValueY) }

    LaunchedEffect(key1 = triggerAnim) {
        if (triggerAnim) {
            launch {
                offsetX.animateTo(
                    targetValue = targetValueX,
                    animationSpec =
                        tween(
                            durationMillis = 2000,
                            delayMillis = 0,
                        ),
                )
            }
            launch {
                offsetY.animateTo(
                    targetValue = targetValueY,
                    animationSpec =
                        tween(
                            durationMillis = 2000,
                            delayMillis = 0,
                        ),
                )
            }
        }
    }
    PlayCard(
        card = card,
        modifier =
            modifier.offset {
                IntOffset(
                    offsetX.value.toInt(),
                    offsetY.value.toInt(),
                )
            },
    )
}

@Composable
fun RivalSideBoard(
    modifier: Modifier = Modifier,
    triggerDrawCardAnim: Boolean,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )
        PlayingCardWithAnimation(
            modifier =
                Modifier
                    .padding(24.dp)
                    .size(96.dp),
            card = R.drawable.clubs_ace__dark_no,
            targetValueX = 100f,
            targetValueY = 100f,
            triggerAnim = triggerDrawCardAnim,
        )
    }
}

@Composable
fun PlayerSideBoard(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap,
    triggerDrawCardAnim: Boolean,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )
        PlayingCardWithAnimation(
            modifier =
                Modifier
                    .padding(24.dp)
                    .size(96.dp)
                    .align(Alignment.BottomEnd),
            card = R.drawable.clubs_ace__dark_no,
            targetValueX = 100f,
            targetValueY = 100f,
            triggerAnim = triggerDrawCardAnim,
        )
    }
}

@Composable
fun RivalSideBoard(
    modifier: Modifier = Modifier,
    points: Int = 0,
) {
    Box(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .wrapContentSize()
                    .align(Alignment.TopStart),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PlayCard(modifier = Modifier.size(80.dp), 0)
            Text(text = "Crimpy", color = Color.Red, fontSize = 24.sp)
        }
        Text(
            text = "$points",
            modifier = Modifier.align(Alignment.TopEnd),
            color = Color.Red,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun PlayerSideBoard(
    modifier: Modifier = Modifier,
    userName: String,
    image: ImageBitmap,
    points: Int = 0,
) {
    Box(modifier = modifier) {
        Image(
            bitmap = image,
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxSize()
                    .zIndex(1f),
        )
        Text(
            text = userName,
            color = Color.Black,
            fontSize = 24.sp,
            modifier =
                Modifier.align(
                    Alignment.TopStart,
                ),
        )
        Text(
            text = "$points",
            modifier = Modifier.align(Alignment.TopEnd),
            color = Color.Black,
            fontSize = 16.sp,
        )
        PlayCard(modifier = Modifier.align(Alignment.BottomEnd), 0)
    }
}

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
