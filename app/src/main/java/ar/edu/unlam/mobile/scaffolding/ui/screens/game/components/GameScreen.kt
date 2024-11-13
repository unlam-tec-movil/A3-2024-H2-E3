package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.GameViewModel
import ar.edu.unlam.mobile.scaffolding.ui.utils.toImageBitmap

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GameScreenPreview(viewModel: GameViewModel = hiltViewModel()) {
    var playerCardCoordinates by remember { mutableStateOf(Offset.Zero) }
    var rivalCardCoordinates by remember { mutableStateOf(Offset.Zero) }
    var deckCoordinates by remember { mutableStateOf(Offset.Zero) }
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        GameBoard(
            modifier = Modifier.matchParentSize(),
            roundNumber = { 1 },
            userImage =
                uiState.userImage?.toImageBitmap() ?: ResourcesCompat
                    .getDrawable(
                        LocalContext.current.resources,
                        R.drawable.default_player_background,
                        null,
                    )!!
                    .toBitmap()
                    .asImageBitmap(),
            deckCoordinates = { deckCoordinates = it },
            playerCardCoordinates = { playerCardCoordinates = it },
            rivalCardCoordinates = { rivalCardCoordinates = it },
        )
        Column(
            modifier = Modifier.wrapContentSize().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DicePair(
                diceOne = Dice.ONE,
                diceTwo = Dice.TWO,
                modifier = Modifier.size(80.dp),
            )
            Button(
                onClick = viewModel::throwDices,
                enabled = uiState.throwButtonEnabled,
            ) {
                Text("Tirar dados")
            }
        }
    }
}
