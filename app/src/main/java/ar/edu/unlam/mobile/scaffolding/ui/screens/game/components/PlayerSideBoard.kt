package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.PlayCard

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
fun PlayerSideBoard(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap,
    onPlaced: (Offset) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )
        PlayCard(
            card = R.drawable.clubs_ace__dark_no,
            modifier =
                Modifier.padding(16.dp).size(96.dp).align(Alignment.BottomEnd).alpha(0f).onGloballyPositioned {
                    onPlaced(it.positionOnScreen())
                },
        )
    }
}
