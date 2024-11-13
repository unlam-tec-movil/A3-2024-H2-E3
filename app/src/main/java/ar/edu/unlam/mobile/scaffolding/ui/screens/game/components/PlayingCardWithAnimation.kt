package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.components.PlayCard

@Composable
fun PlayingCardWithAnimation(
    modifier: Modifier = Modifier,
    card: Int,
    targetValueX: Float,
    targetValueY: Float,
    triggerAnim: Boolean,
) {
    val transition =
        updateTransition(targetState = triggerAnim, label = "PlayingCardWithAnimation Transition")
    val cardSize by transition.animateDp(transitionSpec = {
        tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
    }, label = "CardSize Animation") {
        if (it) 96.dp else 50.dp
    }
    val cardVisibility by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 100, easing = LinearEasing) },
        label = "CardVisibility Animation",
    ) {
        if (it) 1f else 0.0f
    }
    val cardOffset by transition.animateOffset(transitionSpec = {
        tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
    }, label = "Animation Card") {
        if (it) Offset(targetValueX, targetValueY) else Offset.Zero
    }

    PlayCard(
        card = card,
        modifier =
            modifier
                .alpha(cardVisibility)
                .size(cardSize)
                .offset(cardOffset.x.dp, cardOffset.y.dp),
    )
}
