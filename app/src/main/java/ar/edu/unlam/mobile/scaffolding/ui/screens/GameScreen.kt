package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CardDeck
import ar.edu.unlam.mobile.scaffolding.ui.components.Dice
import ar.edu.unlam.mobile.scaffolding.ui.components.PlayCard

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
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

@Composable
fun RivalSideBoard(
    modifier: Modifier = Modifier,
    points: Int = 0,
) {
    Box(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .wrapContentSize()
                    .align(Alignment.TopStart),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PlayCard(modifier = Modifier.size(80.dp), 0)
            Text(text = "Crimpy", color = Color.Red, fontSize = 24.sp)
        }
        Text(
            text = "$points",
            modifier = Modifier.align(Alignment.TopEnd),
            color = Color.Red,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun PlayerSideBoard(
    modifier: Modifier = Modifier,
    userName: String,
    image: ImageBitmap,
    points: Int = 0,
) {
    Box(modifier = modifier) {
        Image(
            bitmap = image,
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxSize()
                    .zIndex(1f),
        )
        Text(
            text = userName,
            color = Color.Black,
            fontSize = 24.sp,
            modifier =
                Modifier.align(
                    Alignment.TopStart,
                ),
        )
        Text(
            text = "$points",
            modifier = Modifier.align(Alignment.TopEnd),
            color = Color.Black,
            fontSize = 16.sp,
        )
        PlayCard(modifier = Modifier.align(Alignment.BottomEnd), 0)
    }
}

@Composable
fun GameBoard(
    modifier: Modifier = Modifier,
    roundNumber: () -> Int = { 1 },
    userImage: ImageBitmap,
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.matchParentSize()) {
            RivalSideBoard(modifier = Modifier.weight(1f))
            PlayerSideBoard(
                modifier = Modifier.weight(1f),
                userName = "",
                image = userImage,
            )
        }
        CardDeck(
            modifier =
                Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp),
        )
        Text(
            text = stringResource(R.string.ronda, roundNumber()),
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = 24.sp,
        )
    }
}
