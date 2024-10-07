package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class GameState(
    val dicePair: Pair<Dice, Dice> = Pair(Dice.ONE, Dice.ONE),
    val diceThrowResult: Int = 0,
)

@HiltViewModel
class GameViewModel
    @Inject
    constructor(
        private val gameUseCases: GameUseCases,
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
    }
