package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.CardType
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import java.util.Locale

@Composable
fun CardDeck(modifier: Modifier = Modifier) {
    PlayCard(
        modifier = modifier,
        card = R.drawable.box_white_on,
    )
}

@Composable
fun PlayCard(
    modifier: Modifier = Modifier,
    card: Int,
) {
    Image(
        painter = painterResource(id = card),
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
fun rememberPlayCard(
    cardValue: CardValue,
    cardType: CardType,
): Int {
    val resourceName = cardType.name.lowercase(Locale.getDefault()) + "_" + cardValue.name.lowercase(Locale.getDefault())
    val resId = LocalContext.current.resources.getIdentifier(resourceName, "drawable", LocalContext.current.packageName)
    return remember {
        resId
    }
}
