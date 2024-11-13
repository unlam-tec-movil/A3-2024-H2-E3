package ar.edu.unlam.mobile.scaffolding.ui.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.MenuButton

@Composable
fun MenuScreen(onStartGameClick: () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.payaso_2),
                    contentScale = ContentScale.Crop,
                ).padding(horizontal = 45.dp, vertical = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleMenu()
        Spacer(modifier = Modifier.height(200.dp))
        MenuButton(text = "Nueva partida", onClick = onStartGameClick)
    }
}

@Composable
fun TitleMenu(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().height(55.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.menutitle_png),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
