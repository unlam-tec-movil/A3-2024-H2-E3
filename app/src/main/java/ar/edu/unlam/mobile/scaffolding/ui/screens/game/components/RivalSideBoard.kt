package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
fun RivalSideBoard(
    modifier: Modifier = Modifier,
    onPlaced: (Offset) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.payaso_2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
        )
        PlayCard(
            card = R.drawable.clubs_ace__dark_no,
            modifier =
                Modifier.padding(16.dp).size(96.dp).align(Alignment.TopStart).alpha(0f).onGloballyPositioned {
                    onPlaced(it.positionOnScreen())
                },
        )
    }
}
