package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class GameState(
    val dicePair: Pair<Dice, Dice> = Pair(Dice.ONE, Dice.ONE),
    val diceThrowResult: Int = 0,
    val playerCard: PlayCard? = null,
    val rivalCard: PlayCard? = null,
)

@HiltViewModel
class GameViewModel
@Inject
constructor(
    private val gameUseCases: GameUseCases,
    private val playCardUseCases: PlayCardUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    fun throwDices() {
        val dicePair = gameUseCases.getRandomDicePair()
        _state.value =
            _state.value.copy(
                dicePair = dicePair,
                diceThrowResult = gameUseCases.getDiceThrowResult(dicePair),
            )
    }

    fun onDrawCard() {
        _state.update {
            it.copy(
                playerCard = playCardUseCases.drawCard(),
                rivalCard = playCardUseCases.drawCard()
            )
        }
    }
}

