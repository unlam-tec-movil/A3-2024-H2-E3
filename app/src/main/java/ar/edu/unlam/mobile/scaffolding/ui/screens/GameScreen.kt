package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CardDeck
import ar.edu.unlam.mobile.scaffolding.ui.components.Dice

@Composable
fun GameScreen(viewModel: GameViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text("Sumaste: ${state.diceThrowResult}")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Dice(
                dice = state.dicePair.first,
                modifier = Modifier.size(80.dp),
            )
            Dice(
                dice = state.dicePair.second,
                modifier = Modifier.size(80.dp),
            )
        }
        CardDeck()
        Button(
            onClick = viewModel::throwDices,
        ) {
            Text("Tirar dados")
        }
        Button(
            onClick = viewModel::onDrawCard,
        ) {
            Text("Sacar carta")
        }
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    Box {
        Column {
            RivalSideBoard()
            PlayerSideBoard()
        }
    }
}

@Composable
fun RivalSideBoard(modifier: Modifier = Modifier) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
        )
    }
}

@Composable
fun PlayerSideBoard(modifier: Modifier = Modifier) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
        )
    }
}
