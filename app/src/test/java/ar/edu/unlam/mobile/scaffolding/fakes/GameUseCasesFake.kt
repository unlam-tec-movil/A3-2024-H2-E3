package ar.edu.unlam.mobile.scaffolding.fakes

import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GameUseCasesFake : GameUseCases {
    private val diceFlow = MutableSharedFlow<Dice>()

    fun emitDicePair(dice: Dice) {
        diceFlow.tryEmit(dice)
    }

    override fun getRandomDice(useShake: Boolean): Flow<Dice> = diceFlow
}
