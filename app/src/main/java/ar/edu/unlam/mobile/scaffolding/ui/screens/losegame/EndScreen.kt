package ar.edu.unlam.mobile.scaffolding.ui.screens.losegame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ar.edu.unlam.mobile.scaffolding.ui.screens.hackmessage.TypingTextAnimation

@Composable
fun EndScreen(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = modifier.background(Color.Black)) {
        TypingTextAnimation(text = text)
    }
}
