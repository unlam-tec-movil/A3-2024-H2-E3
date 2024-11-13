package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GameUseCases {
    fun getRandomDicePair(): Flow<Pair<Dice, Dice>> =
        flow {
            val dice1 = Dice.values().random()
            val dice2 = Dice.values().random()
            emit(Pair(dice1, dice2))
        }

    fun getRandomDicePairForCPU(): Flow<Pair<Dice, Dice>>

    fun getDiceThrowResult(dicePair: Pair<Dice, Dice>): Int = dicePair.first.value + dicePair.second.value
}
