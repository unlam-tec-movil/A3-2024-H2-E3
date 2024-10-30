package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import kotlinx.coroutines.flow.Flow

interface GameUseCases {
    fun getRandomDicePair(): Flow<Pair<Dice, Dice>>

    fun getDiceThrowResult(dicePair: Pair<Dice, Dice>): Int
}
