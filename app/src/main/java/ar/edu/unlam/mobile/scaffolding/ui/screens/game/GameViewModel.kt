package ar.edu.unlam.mobile.scaffolding.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val dice: Dice = Dice.ONE,
    val diceThrowResult: Int = 0,
    val throwButtonEnabled: Boolean = true,
    val playerCard: PlayCard? = null,
    val rivalCard: PlayCard? = null,
    val userImage: ByteArray? = null,
    val drawCard: Boolean = false,
    val playerPoints: Int = 0,
    val cpuPoints: Int = 0,
    val currentRound: Int = 1,
    val maxRounds: Int = 3,
    val currentPart: Int = 1,
    val maxParts: Int = 3,
    val playerOverallScore: Int = 0,
    val cpuOverallScore: Int = 0,
    val thirdDiceEnabled: Boolean = false,
    val gameOver: Boolean = false,
    val winner: String? = null,
    val isPlayerTurn: Boolean = false,
    val isDrawingCard: Boolean = false,
    val rivalDiceResult: Int = 0,
    val showStatusMessage: Boolean = false,
    val statusMessage: String = "",
    val showMap: Boolean = false,
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

        fun startGame() {
            viewModelScope.launch {
                _state.value = GameState()
                getUserPhotoUseCases.getPhoto().let { userImage ->
                    _state.update { currentState ->
                        currentState.copy(userImage = userImage?.photo)
                    }
                }
                playerDrawCard()
            }
        }

        fun startPart() {
            _state.update { currentState ->
                currentState.copy(
                    playerPoints = 0,
                    cpuPoints = 0,
                    currentRound = 1,
                )
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

        suspend fun playerDrawCard() {
            val playerCard = playCardUseCases.drawCard()
            _state.update { currentState ->
                currentState.copy(
                    playerCard = playerCard,
                    diceThrowResult = 0,
                    isPlayerTurn = true,
                    isDrawingCard = true,
                )
            }
            showStatusMessage("Tu dado deberá dar ${playerCard.value.numericValue}")
            enableThrowButton(true)
        }

        fun playerThrowDices() {
            viewModelScope.launch {
                enableThrowButton(false)
                gameUseCases.getRandomDice(true).collect { dice ->
                    _state.update { currentState ->
                        currentState.copy(
                            dice = dice,
                            diceThrowResult = dice.value,
                        )
                    }
                    delay(2000)
                    var playerPoints: Int? = null

                    if (_state.value.playerCard
                            ?.value
                            ?.numericValue == dice.value
                    ) {
                        showStatusMessage("Coincide suma puntos")
                        playerPoints = _state.value.playerPoints + 1
                    } else {
                        showStatusMessage("No Coincide, no suma puntos")
                        _state.value.playerPoints
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
                showStatusMessage("Tu dado deberá dar ${cpuCard.value.numericValue}")
                gameUseCases.getRandomDice(false).collect { dice ->
                    delay(1000)
                    _state.update { currentState ->
                        currentState.copy(rivalDiceResult = dice.value, dice = dice)
                    }
                    delay(2000)

                    var cpuPoints: Int? = null

                    if (_state.value.rivalCard
                            ?.value
                            ?.numericValue == dice.value
                    ) {
                        showStatusMessage("Coincide suma puntos")
                        cpuPoints = _state.value.cpuPoints + 1
                    } else {
                        showStatusMessage("No Coincide, no suma puntos")
                        _state.value.cpuPoints
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

        fun nextRound() {
            if (_state.value.currentRound == _state.value.maxRounds) {
                _state.update { currentState ->
                    currentState.copy(currentRound = 1)
                }
                nextPart()
            } else {
                _state.update { currentState ->
                    currentState.copy(currentRound = currentState.currentRound + 1)
                }
            }
        }

        fun nextPart() {
            // Determine the winner of the part and update the overall score
            if (_state.value.playerPoints > _state.value.cpuPoints) {
                _state.update { currentState ->
                    currentState.copy(playerOverallScore = currentState.playerOverallScore + 1)
                }
            } else if (_state.value.cpuPoints > _state.value.playerPoints) {
                _state.update { currentState ->
                    currentState.copy(cpuOverallScore = currentState.cpuOverallScore + 1)
                }
            }

            if (_state.value.currentPart == _state.value.maxParts) {
                _state.update { currentState ->
                    currentState.copy(gameOver = true)
                }
                determineFinalWinner()
            } else {
                _state.update { currentState ->
                    currentState.copy(showMap = true)
                }
            }
        }

        fun onShowMapEnd() {
            _state.update { currentState ->
                currentState.copy(showMap = false, currentPart = currentState.currentPart + 1)
            }
            startPart()
        }

        fun determineFinalWinner() {
            if (_state.value.playerOverallScore > _state.value.cpuOverallScore) {
                _state.update {
                    it.copy(winner = "Jugador")
                }
            } else if (_state.value.cpuOverallScore > _state.value.playerOverallScore) {
                _state.update {
                    it.copy(winner = "CPU")
                }
            } else {
                _state.update {
                    it.copy(winner = "Nadie - Empate")
                }
            }
        }

        suspend fun switchTurn() {
            if (_state.value.gameOver) return
            if (_state.value.isPlayerTurn) {
                cpuTurn()
            } else {
                playerDrawCard()
            }
        }

        fun checkIfIsGameOver() {
            if (_state.value.currentRound == _state.value.maxRounds &&
                _state.value.currentPart == _state.value.maxParts
            ) {
                _state.update {
                    it.copy(gameOver = true)
                }
                determineFinalWinner()
            }
        }

        private fun enableThrowButton(value: Boolean) {
            _state.update { it.copy(throwButtonEnabled = value) }
        }
    }
