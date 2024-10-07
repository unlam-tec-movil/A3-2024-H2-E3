package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun Dice(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(30.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.dice_5),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
private fun DicePreview() {
    Dice()
}
