package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.ui.components.Dice

@Composable
fun DicePair(
    modifier: Modifier = Modifier,
    diceOne: Dice,
    diceTwo: Dice,
) {
    Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Dice(
            dice = diceOne,
            modifier = modifier,
        )
        Dice(
            dice = diceTwo,
            modifier = modifier,
        )
    }
}
