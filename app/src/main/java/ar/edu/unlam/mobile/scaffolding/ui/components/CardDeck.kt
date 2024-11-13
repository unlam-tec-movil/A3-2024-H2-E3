package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.models.toNumberName
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
fun getCardImage(card: PlayCard): Int {
    val resourceName =
        card.type.name.lowercase(Locale.getDefault()) + "_" + card.value.toNumberName()
    val resId =
        LocalContext.current.resources.getIdentifier(
            resourceName,
            "drawable",
            LocalContext.current.packageName,
        )
    return resId
}
