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
import kotlinx.coroutines.Dispatchers
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
    val isPlayerTurn: Boolean = true,
    val rivalDiceResult: Int = 0,
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

        fun startGame(maxRounds: Int) {
            _state.value = GameState(maxRounds = maxRounds)
            playerDrawCard() // Empezar tomando carta
        }

        fun playerDrawCard() {
            viewModelScope.launch {
                val playerCard = playCardUseCases.drawCard()
                _state.update { currentState ->
                    currentState.copy(
                        playerCard = playerCard,
                        diceThrowResult = 0,
                    )
                }
            }
        }

        fun playerThrowDices() {
            viewModelScope.launch(Dispatchers.IO) {
                enableThrowButton(false)
                println("hola es tu turno")

                gameUseCases.getRandomDicePair().collect { dicePair ->
                    val diceThrowResult = gameUseCases.getDiceThrowResult(dicePair)
                    _state.update { currentState ->
                        currentState.copy(
                            dicePair = dicePair,
                            diceThrowResult = diceThrowResult,
                        )
                    }

                    var playerPoints: Int? = null

                    when (_state.value.playerCard?.value) {
                        CardValue.ACE -> {
                            println("hola detectamos carta ${_state.value.playerCard}")
                            println("hola detectamos el dado ${_state.value.dicePair.first.value}")
                            if (dicePair.first.value == CardValue.ACE.numericValue
                            ) {
                                println("hola los puntos son ${_state.value.playerPoints}")
                                playerPoints = _state.value.playerPoints + 1
                                println("hola y ahora los puntos son ${_state.value.playerPoints}")
                            } else {
                                playerPoints = _state.value.playerPoints
                                nextRound()
                                switchTurn()
                            }

                            checkIfIsGameOver()
                        }

                        CardValue.KING -> {
                            println("hola detectamos carta ${_state.value.playerCard}")
                            if (dicePair.first.value + diceThrowResult == CardValue.KING.numericValue
                            ) {
                                playerPoints = _state.value.playerPoints + 1
                            } else {
                                playerPoints = _state.value.playerPoints
                                nextRound()
                                switchTurn()
                            }
                        }

                        else -> {
                            println("hola detectamos carta ${_state.value.playerCard}")
                            if (_state.value.playerCard
                                    ?.value
                                    ?.numericValue == diceThrowResult
                            ) {
                                playerPoints = _state.value.playerPoints + 1
                            } else {
                                _state.value.playerPoints
                            }
                            checkIfIsGameOver()
                            // Si la carta no coincide con los dados, el turno pasa a la CPU

                            println("hola el valor es $diceThrowResult ")
                            if (_state.value.playerCard
                                    ?.value
                                    ?.numericValue != diceThrowResult
                            ) {
                                println("hola esta por cambiar el turno")
                                nextRound()
                                switchTurn()
                            }
                        }
                    }

                    // Actualiza estado
                    playerPoints?.let {
                        _state.update { currentState ->
                            currentState.copy(
                                playerPoints = playerPoints,
                            )
                        }
                    }
                }
                enableThrowButton(true)
            }
        }

        private suspend fun cpuTurn() {
            if (_state.value.gameOver) return

            println("holaCPU es el turno de la cpu")
            delay(2000)

            viewModelScope.launch {
                val cpuCard = playCardUseCases.drawCard() // La CPU saca una carta
                println("holaCPU el valor de la carta de la cpu es ${cpuCard.value}")
                _state.update { currentState ->
                    currentState.copy(rivalCard = cpuCard)
                }

                gameUseCases.getRandomDicePairForCPU().collect { dicePair ->

                    val diceThrowResult = gameUseCases.getDiceThrowResult(dicePair)
                    println("holaCPU el valor de lo dados de la cpu es $diceThrowResult")
                    _state.update { currentState ->
                        currentState.copy(rivalDiceResult = diceThrowResult)
                    }

                    var cpuPoints: Int? = null

                    when (_state.value.rivalCard?.value) {
                        CardValue.ACE -> {
                            println("holaCPU detectamos carta ${_state.value.rivalCard}")
                            println("holaCPU detectamos el dado ${_state.value.dicePair.first.value}")
                            if (dicePair.first.value == CardValue.ACE.numericValue
                            ) {
                                println("holaCPU los puntos son ${_state.value.cpuPoints}")
                                cpuPoints = _state.value.cpuPoints + 1
                                println("holaCPU y ahora los puntos son ${_state.value.cpuPoints}")
                                cpuTurn()
                            } else {
                                cpuPoints = _state.value.cpuPoints
                                nextRound()
                                switchTurn()
                            }

                            checkIfIsGameOver()
                        }

                        CardValue.KING -> {
                            println("holaCPU detectamos carta ${_state.value.rivalCard}")
                            if (dicePair.first.value + diceThrowResult == CardValue.KING.numericValue
                            ) {
                                cpuPoints = _state.value.cpuPoints + 1
                                cpuTurn()
                            } else {
                                cpuPoints = _state.value.cpuPoints
                                nextRound()
                                switchTurn()
                            }
                        }

                        else -> {
                            println("holaCPU detectamos carta ${_state.value.rivalCard}")
                            if (_state.value.rivalCard
                                    ?.value
                                    ?.numericValue == diceThrowResult
                            ) {
                                cpuPoints = _state.value.cpuPoints + 1
                                cpuTurn()
                            } else {
                                _state.value.cpuPoints
                            }
                            checkIfIsGameOver()

                            println("holaCPU el valor es $diceThrowResult ")
                            if (_state.value.rivalCard
                                    ?.value
                                    ?.numericValue != diceThrowResult
                            ) {
                                println("holaCPU esta por cambiar el turno")
                                nextRound()
                                switchTurn()
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

            println("hola no fue game Over")

            println("hola el primer estado es isplayerturn: ${_state.value.isPlayerTurn}")

            _state.update { currentState ->
                currentState.copy(isPlayerTurn = !currentState.isPlayerTurn)
            }
            println("hola el estado luego es isplayerturn: ${_state.value.isPlayerTurn}")

            if (!_state.value.isPlayerTurn) {
                println("hola el estado luego es isplayerturn: ${_state.value.isPlayerTurn}")
                cpuTurn()
            } else {
                // playerDrawCard()
            }
        }

        private fun checkIfIsGameOver() {
            if (_state.value.currentRound == _state.value.maxRounds) {
                if (_state.value.playerPoints > _state.value.cpuPoints
                ) {
                    _state.update {
                        it.copy(gameOver = true, winner = "Player")
                    }
                    println("hola ganó player Game Over")
                } else if (_state.value.cpuPoints > _state.value.playerPoints) {
                    _state.update {
                        it.copy(gameOver = true, winner = "CPU")
                    }
                    println("hola ganó CPU Game Over")
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
