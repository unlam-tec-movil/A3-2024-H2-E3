package ar.edu.unlam.mobile.scaffolding.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.usecases.CaptureUserPhotoUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameState(
    val dicePair: Pair<Dice, Dice> = Pair(Dice.ONE, Dice.ONE),
    val diceThrowResult: Int = 0,
    val throwButtonEnabled: Boolean = true,
    val playerCard: PlayCard? = null,
    val rivalCard: PlayCard? = null,
    val userImage: ByteArray? = null,
    val drawCard: Boolean = false,
)

@HiltViewModel
class GameViewModel
    @Inject
    constructor(
        private val gameUseCases: GameUseCases,
        private val playCardUseCases: PlayCardUseCases,
        private val getUserPhotoUseCases: CaptureUserPhotoUseCases,
    ) : ViewModel() {
        private val _state = MutableStateFlow(GameState())
        val state = _state.asStateFlow()

        init {
            viewModelScope.launch {
                getUserPhotoUseCases.getPhoto()?.photo.let { photo ->
                    _state.update {
                        it.copy(
                            userImage = photo,
                        )
                    }
                }
            }
        }

        fun throwDices() {
            viewModelScope.launch(Dispatchers.IO) {
                enableThrowButton(false)
                gameUseCases.getRandomDicePair().collect {
                    _state.value =
                        _state.value.copy(
                            dicePair = it,
                            diceThrowResult = gameUseCases.getDiceThrowResult(it),
                        )
                }
                enableThrowButton(true)
            }
        }

        fun onDrawCard() {
            _state.update {
                it.copy(
                    playerCard = playCardUseCases.drawCard(),
                    rivalCard = playCardUseCases.drawCard(),
                )
            }
        }

        private fun enableThrowButton(value: Boolean) {
            _state.update {
                it.copy(
                    throwButtonEnabled = value,
                )
            }
        }
    }
