package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.theme.DarkBlue
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightBlue

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier =
            modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    Brush.linearGradient(0f to LightBlue, 1f to DarkBlue),
                    RoundedCornerShape(9.dp),
                ),
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 19.sp,
            color = Color.White,
            maxLines = 1,
            fontFamily = FontFamily(Font(R.font.inter_medium)),
        )
    }
}

@Preview
@Composable
private fun MenuButtonPrev() {
    MenuButton("Comenzar", {})
}
