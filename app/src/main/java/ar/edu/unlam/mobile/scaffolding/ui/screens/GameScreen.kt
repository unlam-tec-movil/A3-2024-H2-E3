package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CardDeck
import ar.edu.unlam.mobile.scaffolding.ui.components.Dice
import ar.edu.unlam.mobile.scaffolding.ui.components.PlayCard
import kotlinx.coroutines.launch

@Composable
fun GameScreen(viewModel: GameViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text("Sumaste: ${state.diceThrowResult}")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Dice(
                dice = state.dicePair.first,
                modifier = Modifier.size(80.dp),
            )
            Dice(
                dice = state.dicePair.second,
                modifier = Modifier.size(80.dp),
            )
        }
        CardDeck()
        Button(
            onClick = viewModel::throwDices,
        ) {
            Text("Tirar dados")
        }
        Button(
            onClick = viewModel::onDrawCard,
        ) {
            Text("Sacar carta")
        }
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
