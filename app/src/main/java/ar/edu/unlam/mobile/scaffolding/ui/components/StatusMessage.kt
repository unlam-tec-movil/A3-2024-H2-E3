package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.ui.theme.BorderCardMessage
import ar.edu.unlam.mobile.scaffolding.ui.theme.CardMessage

@Composable
fun StatusMessage(
    isPlayerTurn: Boolean,
    statusMesssage: String,
) {
    Card(
        colors =
            CardDefaults.cardColors(
                contentColor = CardMessage,
            ),
        modifier =
            Modifier
                .width(430.dp)
                .height(199.dp)
                .border(
                    width = 1.dp,
                    color = BorderCardMessage,
                    shape = RoundedCornerShape(25.dp),
                ).clip(RoundedCornerShape(25.dp))
                .background(
                    color = CardMessage,
                ).alpha(0.8f)
                .fillMaxSize(),
        shape = RoundedCornerShape(25.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(CardMessage),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Turno de ${if (isPlayerTurn) "Jugador" else "Cpu"}",
                textAlign = TextAlign.Center,
                color = Color.Red,
                fontSize = 25.sp,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = statusMesssage,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 25.sp,
            )
        }
    }
}
