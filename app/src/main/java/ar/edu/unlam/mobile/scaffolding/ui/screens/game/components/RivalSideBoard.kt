package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.PlayCard

@Composable
fun RivalSideBoard(
    modifier: Modifier = Modifier,
    points: Int = 0,
    onPlaced: (Offset) -> Unit,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.payaso_2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
        )
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.TopStart),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PlayCard(
                card = R.drawable.clubs_ace,
                modifier =
                    Modifier
                        .padding(16.dp)
                        .size(96.dp)
                        .alpha(1f)
                        .onGloballyPositioned {
                            onPlaced(it.positionOnScreen())
                        },
            )
            Text(text = "Crimpy", color = Color.Red, fontSize = 32.sp)
        }
        Text(
            text = "$points",
            modifier = Modifier.padding(16.dp).align(Alignment.TopEnd),
            color = Color.Red,
            fontSize = 56.sp,
        )
    }
}
