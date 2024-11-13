package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CardDeck

@Composable
fun DicesAndDeck(
    modifier: Modifier = Modifier,
    triggerDrawCardAnim: Boolean,
    playerCardCoordinates: Offset,
    rivalCardCoordinates: Offset,
    deckCoordinates: Offset,
) {
    val density = LocalDensity.current

    Column(
        modifier =
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(modifier = Modifier.wrapContentSize()) {
                PlayingCardWithAnimation(
                    modifier = Modifier,
                    card = R.drawable.clubs_ace,
                    targetValueX = with(density) { rivalCardCoordinates.x.toDp().value - deckCoordinates.x.toDp().value },
                    targetValueY = with(density) { rivalCardCoordinates.y.toDp().value - deckCoordinates.y.toDp().value },
                    triggerAnim = triggerDrawCardAnim,
                    deckCoordinates = Offset.Zero,
                )
                PlayingCardWithAnimation(
                    modifier = Modifier,
                    card = R.drawable.clubs_ace,
                    targetValueX = with(density) { playerCardCoordinates.x.toDp().value - deckCoordinates.x.toDp().value },
                    targetValueY = with(density) { playerCardCoordinates.y.toDp().value - deckCoordinates.y.toDp().value },
                    triggerAnim = triggerDrawCardAnim,
                    deckCoordinates = Offset.Zero,
                )
            }
        }
    }
}
