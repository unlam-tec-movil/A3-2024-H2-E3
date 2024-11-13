package ar.edu.unlam.mobile.scaffolding.ui.screens.hackmessage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun HackMessagesScreen(navController: NavController) {
    val messages =
        listOf(
            "Hemos tomado control absoluto de tu celular",
            "Solo podrás librarte de nosotros ganando el juego",
        )

    var currentMessageIndex by remember { mutableStateOf(0) }
    val message = messages[currentMessageIndex]

    var isMessageComplete by remember { mutableStateOf(false) }
    var isLastMessage by remember { mutableStateOf(false) }

    // Amimación el texto se escribe caracter a caracter y desaparace una vez completada la frase
    val alpha by animateFloatAsState(
        targetValue = if (isMessageComplete) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        finishedListener = {
            if (isMessageComplete) {
                // Cambiamos al siguiente msj
                if (currentMessageIndex == messages.lastIndex) {
                    isLastMessage = true
                } else {
                    currentMessageIndex = (currentMessageIndex + 1) % messages.size
                }
                isMessageComplete = false
            }
        },
    )

    if (isLastMessage) {
        LaunchedEffect(Unit) {
            navController.navigate("captureUserPhoto")
        }
    }
    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            TypingTextAnimation(
                text = message,
                textColor = Color.White,
                delayMillis = 100L,
                alpha = alpha,
                onTypingAnimationComplete = {
                    isMessageComplete = true
                },
            )
        }
    }
}

@Composable
fun TypingTextAnimation(
    text: String,
    textColor: Color = Color.White,
    delayMillis: Long = 100L,
    alpha: Float = 1f,
    onTypingAnimationComplete: () -> Unit = {},
) {
    var visibleText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        visibleText = ""
        text.forEach { char ->
            visibleText += char
            delay(delayMillis)
        }
        onTypingAnimationComplete()
    }

    Text(
        text = visibleText,
        color = textColor,
        fontSize = 74.sp,
        lineHeight = 58.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.alpha(alpha),
    )
}

@RequiresApi(Build.VERSION_CODES.R)
@Preview(showBackground = true)
@Composable
fun HackMessagesPreview() {
    val navController = rememberNavController()
    HackMessagesScreen(navController)
}
