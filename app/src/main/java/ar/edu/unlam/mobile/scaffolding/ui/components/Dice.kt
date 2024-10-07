package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice

@Composable
fun Dice(
    dice: Dice,
    modifier: Modifier = Modifier,
) {
    val imageId =
        when (dice) {
            Dice.ONE -> R.drawable.dice_1
            Dice.TWO -> R.drawable.dice_2
            Dice.THREE -> R.drawable.dice_3
            Dice.FOUR -> R.drawable.dice_4
            Dice.FIVE -> R.drawable.dice_5
            Dice.SIX -> R.drawable.dice_6
        }
    Box(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
private fun DicePreview() {
    Dice(Dice.FIVE)
}
