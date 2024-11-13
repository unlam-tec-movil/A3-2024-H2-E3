package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.ui.components.CardDeck
import ar.edu.unlam.mobile.scaffolding.ui.components.Dice

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text("Sumaste: ${state.diceThrowResult}")
        Dices(firstDice = state.dicePair.first, secondDice = state.dicePair.second)
        CardDeck()
        Button(
            onClick = viewModel::throwDices,
            enabled = state.throwButtonEnabled,
        ) {
            Text("Tirar dados")
        }
        Button(
            onClick = viewModel::onDrawCard,
        ) {
            Text("Sacar carta")
        }
        Button(
            onClick = { navController.navigate("location_screen") },
        ) {
            Text("Ubicación")
        }
    }
}

@Composable
fun Dices(
    firstDice: Dice,
    secondDice: Dice,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Dice(
            dice = firstDice,
            modifier = Modifier.size(80.dp),
        )
        Dice(
            dice = secondDice,
            modifier = Modifier.size(80.dp),
        )
    }
}

