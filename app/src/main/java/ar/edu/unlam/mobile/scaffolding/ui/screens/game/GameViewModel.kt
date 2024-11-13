package ar.edu.unlam.mobile.scaffolding.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.usecases.CaptureUserPhotoUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    val playerPoints: Int = 0,
    val cpuPoints: Int = 0,
    val currentRound: Int = 1,
    val maxRounds: Int = 10,
    val thirdDiceEnabled: Boolean = false,
    val gameOver: Boolean = false,
    val winner: String? = null,
    val isPlayerTurn: Boolean = false,
    val isDrawingCard: Boolean = false,
    val rivalDiceResult: Int = 0,
    val showStatusMessage: Boolean = false,
    val statusMessage: String = "",
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

        fun startGame(maxRounds: Int) {
            viewModelScope.launch {
                _state.value = GameState(maxRounds = maxRounds)
                getUserPhotoUseCases.getPhoto().let { userImage ->
                    _state.update { currentState ->
                        currentState.copy(userImage = userImage?.photo)
                    }
                }
                playerDrawCard()
            }
        }

        private suspend fun showStatusMessage(statusMessage: String) {
            _state.update { currentState ->
                currentState.copy(
                    showStatusMessage = true,
                    statusMessage = statusMessage,
                )
            }
            delay(3000)
            _state.update { currentState ->
                currentState.copy(
                    showStatusMessage = false,
                    statusMessage = "",
                )
            }
        }

        private suspend fun playerDrawCard() {
            val playerCard = playCardUseCases.drawCard()
            _state.update { currentState ->
                currentState.copy(
                    playerCard = playerCard,
                    diceThrowResult = 0,
                    isPlayerTurn = true,
                    isDrawingCard = true,
                )
            }
            showStatusMessage("Tus dados deberan sumar ${playerCard.value.numericValue}")
            enableThrowButton(true)
        }

        fun playerThrowDices() {
            viewModelScope.launch {
                enableThrowButton(false)
                gameUseCases.getRandomDicePair().collect { dicePair ->
                    val diceThrowResult = gameUseCases.getDiceThrowResult(dicePair)
                    _state.update { currentState ->
                        currentState.copy(
                            dicePair = dicePair,
                            diceThrowResult = diceThrowResult,
                        )
                    }
                    delay(2000)
                    var playerPoints: Int? = null

                    when (_state.value.playerCard?.value) {
                        CardValue.ACE -> {
                            playerPoints =
                                if (dicePair.first.value == CardValue.ACE.numericValue
                                ) {
                                    showStatusMessage("Coincide suma puntos")
                                    _state.value.playerPoints + 1
                                } else {
                                    showStatusMessage("No Coincide, no suma puntos")
                                    _state.value.playerPoints
                                }
                        }

                        CardValue.KING -> {
                            playerPoints =
                                if (dicePair.first.value + diceThrowResult == CardValue.KING.numericValue
                                ) {
                                    showStatusMessage("Coincide suma puntos")
                                    _state.value.playerPoints + 1
                                } else {
                                    showStatusMessage("No Coincide, no suma puntos")
                                    _state.value.playerPoints
                                }
                        }

                        else -> {
                            if (_state.value.playerCard
                                    ?.value
                                    ?.numericValue == diceThrowResult
                            ) {
                                showStatusMessage("Coincide suma puntos")
                                playerPoints = _state.value.playerPoints + 1
                            } else {
                                showStatusMessage("No Coincide, no suma puntos")
                                _state.value.playerPoints
                            }
                        }
                    }

                    playerPoints?.let {
                        _state.update { currentState ->
                            currentState.copy(
                                playerPoints = playerPoints,
                            )
                        }
                    }
                    checkIfIsGameOver()
                    nextRound()
                    switchTurn()
                }
            }
        }

        private fun cpuTurn() {
            if (_state.value.gameOver) return
            viewModelScope.launch {
                val cpuCard = playCardUseCases.drawCard() // La CPU saca una carta
                _state.update { currentState ->
                    currentState.copy(rivalCard = cpuCard, isPlayerTurn = false)
                }
                showStatusMessage("Tus dados deberan sumar ${cpuCard.value.numericValue}")
                gameUseCases.getRandomDicePairForCPU().collect { dicePair ->
                    delay(1000)
                    val diceThrowResult = gameUseCases.getDiceThrowResult(dicePair)
                    _state.update { currentState ->
                        currentState.copy(rivalDiceResult = diceThrowResult, dicePair = dicePair)
                    }
                    delay(2000)

                    var cpuPoints: Int? = null

                    when (_state.value.rivalCard?.value) {
                        CardValue.ACE -> {
                            if (dicePair.first.value == CardValue.ACE.numericValue
                            ) {
                                showStatusMessage("Coincide suma puntos")
                                cpuPoints = _state.value.cpuPoints + 1
                            } else {
                                showStatusMessage("No Coincide, no suma puntos")
                                cpuPoints = _state.value.cpuPoints
                            }
                        }

                        CardValue.KING -> {
                            if (dicePair.first.value + diceThrowResult == CardValue.KING.numericValue
                            ) {
                                showStatusMessage("Coincide suma puntos")
                                cpuPoints = _state.value.cpuPoints + 1
                            } else {
                                showStatusMessage("No Coincide, no suma puntos")
                                cpuPoints = _state.value.cpuPoints
                            }
                        }

                        else -> {
                            if (_state.value.rivalCard
                                    ?.value
                                    ?.numericValue == diceThrowResult
                            ) {
                                showStatusMessage("Coincide suma puntos")
                                cpuPoints = _state.value.cpuPoints + 1
                            } else {
                                showStatusMessage("No Coincide, no suma puntos")
                                _state.value.cpuPoints
                            }
                        }
                    }

                    // Actualizar estado
                    cpuPoints?.let {
                        _state.update { currentState ->
                            currentState.copy(
                                cpuPoints = cpuPoints,
                            )
                        }
                    }
                    checkIfIsGameOver()
                    nextRound()
                    switchTurn()
                }
            }
        }

        private fun nextRound() {
            if (_state.value.currentRound == 10) {
                _state.update { currentState ->
                    currentState.copy(currentRound = currentState.currentRound)
                }
            } else {
                _state.update { currentState ->
                    currentState.copy(currentRound = currentState.currentRound + 1)
                }
            }
        }

        private suspend fun switchTurn() {
            if (_state.value.gameOver) return
            if (_state.value.isPlayerTurn) {
                cpuTurn()
            } else {
                playerDrawCard()
            }
        }

        private fun checkIfIsGameOver() {
            if (_state.value.currentRound == _state.value.maxRounds) {
                if (_state.value.playerPoints > _state.value.cpuPoints
                ) {
                    _state.update {
                        it.copy(gameOver = true, winner = "Jugador")
                    }
                } else if (_state.value.cpuPoints > _state.value.playerPoints) {
                    _state.update {
                        it.copy(gameOver = true, winner = "CPU")
                    }
                } else {
                    _state.update {
                        it.copy(gameOver = true, winner = "Nadie - Empate")
                    }
                }
            }
        }

        private fun enableThrowButton(value: Boolean) {
            _state.update { it.copy(throwButtonEnabled = value) }
        }
    }
